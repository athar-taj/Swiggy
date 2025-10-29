package cln.swiggy.restaurant.serviceImpl;

import cln.swiggy.restaurant.exception.ResourceNotFoundException;
import cln.swiggy.restaurant.model.Cart;
import cln.swiggy.restaurant.model.Menu;
import cln.swiggy.restaurant.model.request.CartRequest;
import cln.swiggy.restaurant.model.response.CartResponse;
import cln.swiggy.restaurant.model.response.CommonResponse;
import cln.swiggy.restaurant.repository.CartRepository;
import cln.swiggy.restaurant.repository.MenuRepository;
import cln.swiggy.restaurant.service.CartService;
import org.apache.coyote.BadRequestException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
@CacheConfig(cacheNames = "user_cart")
public class CartServiceImpl implements CartService {

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.routing-key}")
    private String routingKey;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;


    @Override
    @Cacheable(key = "#userId")
    public ResponseEntity<CommonResponse> getCartItems(Long userId) {
        Boolean isUserExists = (Boolean) rabbitTemplate.convertSendAndReceive(exchange, routingKey, userId);
        if (Boolean.FALSE.equals(isUserExists)) throw new ResourceNotFoundException("User not found with id: " + userId);

        List<Cart> cartItems = cartRepository.findByUserId(userId);

        return ResponseEntity.ok(
                new CommonResponse(HttpStatus.OK.value(),
                        "Cart items retrieved successfully",
                        CartResponse.convertToCartResponseWithMenu(cartItems))
        );
    }

    @Override
    @CachePut(key = "#userId")
    public ResponseEntity<CommonResponse> addItemToCart(Long userId, CartRequest request) throws BadRequestException {
        Boolean isUserExists = (Boolean) rabbitTemplate.convertSendAndReceive(exchange, routingKey, userId);
        if (Boolean.FALSE.equals(isUserExists)) throw new ResourceNotFoundException("User not found with id: " + userId);

        Menu menu = menuRepository.findById(request.getMenuId())
                .orElseThrow(() -> new ResourceNotFoundException("Menu item not found with id: " + request.getMenuId()));

        if (!menu.getIsAvailable()) throw new BadRequestException("Menu item is not available");

        Cart cart = new Cart();
        cart.setUserId(userId);
        cart.setCartMenu(menu);
        cart.setQuantity(request.getQuantity());
        cart.setPrice(menu.getPrice());
        cart.setTotalPrice(menu.getPrice() * request.getQuantity());
        cart.setCreatedAt(LocalDateTime.now());

        cartRepository.save(cart);

        List<Cart> updatedCart = cartRepository.findByUserId(userId);
        return ResponseEntity.ok(new CommonResponse(200, "Item added to cart successfully",
                CartResponse.convertToCartResponseWithMenu(updatedCart)));
    }

    @Override
    @CachePut(key = "#userId")
    public ResponseEntity<CommonResponse> updateCartItem(Long userId, Long itemId, CartRequest request) throws BadRequestException {
        Boolean isUserExists = (Boolean) rabbitTemplate.convertSendAndReceive(exchange, routingKey, userId);
        if (Boolean.FALSE.equals(isUserExists)) throw new ResourceNotFoundException("User not found with id: " + userId);

        Cart cart = cartRepository.findByIdAndUserId(itemId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));

        Menu menu = menuRepository.findById(request.getMenuId())
                .orElseThrow(() -> new ResourceNotFoundException("Menu item not found with id: " + request.getMenuId()));

        if (!menu.getIsAvailable()) throw new BadRequestException("Menu item is not available");

        cart.setCartMenu(menu);
        cart.setQuantity(request.getQuantity());
        cart.setPrice(menu.getPrice());
        cart.setTotalPrice(menu.getPrice() * request.getQuantity());
        cartRepository.save(cart);

        List<Cart> updatedCart = cartRepository.findByUserId(userId);
        return ResponseEntity.ok(new CommonResponse(200, "Cart item updated successfully",
                CartResponse.convertToCartResponseWithMenu(updatedCart)));
    }

    @Override
    @CacheEvict(key = "#userId")
    public ResponseEntity<CommonResponse> removeCartItem(Long userId, Long itemId) {
        Boolean isUserExists = (Boolean) rabbitTemplate.convertSendAndReceive(exchange, routingKey, userId);
        if (Boolean.FALSE.equals(isUserExists)) throw new ResourceNotFoundException("User not found with id: " + userId);

        Cart cart = cartRepository.findByIdAndUserId(itemId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));

        cartRepository.delete(cart);

        return ResponseEntity.ok(new CommonResponse(HttpStatus.OK.value(), "Cart item removed successfully", null));
    }
}
