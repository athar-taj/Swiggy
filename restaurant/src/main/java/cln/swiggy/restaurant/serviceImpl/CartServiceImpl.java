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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.routing-key}")
    private String routingKey;

    @Autowired
    CartRepository cartRepository;
    @Autowired
    MenuRepository menuRepository;
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Override
    public ResponseEntity<CommonResponse> getCartItems(Long userId) {

        Boolean isUserExists = (Boolean) rabbitTemplate.convertSendAndReceive(exchange,routingKey, userId);

        if (Boolean.FALSE.equals(isUserExists)) throw new ResourceNotFoundException("User not found with id: " + userId);

        List<Cart> cartItems = cartRepository.findByUserId(userId);

        return ResponseEntity.ok(new CommonResponse(HttpStatus.OK.value(), "Cart items retrieved successfully", CartResponse.convertToCartResponseWithMenu(cartItems)));
    }

    @Override
    public ResponseEntity<CommonResponse> addItemToCart(Long userId, CartRequest request) throws BadRequestException {
        Boolean isUserExists = (Boolean) rabbitTemplate.convertSendAndReceive(exchange, routingKey, userId);
        if (Boolean.FALSE.equals(isUserExists)) {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }

        Menu menu = menuRepository.findById(request.getMenuId())
                .orElseThrow(() -> new ResourceNotFoundException("Menu item not found with id: " + request.getMenuId()));

        if (!menu.getIsAvailable()) {
            throw new BadRequestException("Menu item is not available");
        }

        Cart cart = new Cart();
        cart.setUserId(userId);
        cart.setCartMenu(menu);
        cart.setQuantity(request.getQuantity());
        cart.setPrice(menu.getPrice());
        cart.setTotalPrice(menu.getPrice() * request.getQuantity());
        cart.setCreatedAt(LocalDateTime.now());

        Cart savedCart = cartRepository.save(cart);

        return ResponseEntity.ok(new CommonResponse(HttpStatus.OK.value(), "Item added to cart successfully", savedCart));
    }

    @Override
    public ResponseEntity<CommonResponse> updateCartItem(Long userId, Long itemId, CartRequest request) throws BadRequestException {
        Boolean isUserExists = (Boolean) rabbitTemplate.convertSendAndReceive(exchange, routingKey, userId);
        if (Boolean.FALSE.equals(isUserExists)) {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }

        Cart cart = cartRepository.findByIdAndUserId(itemId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));

        Menu menu = menuRepository.findById(request.getMenuId())
                .orElseThrow(() -> new ResourceNotFoundException("Menu item not found with id: " + request.getMenuId()));

        if (!menu.getIsAvailable()) {
            throw new BadRequestException("Menu item is not available");
        }

        cart.setCartMenu(menu);
        cart.setQuantity(request.getQuantity());
        cart.setPrice(menu.getPrice());
        cart.setTotalPrice(menu.getPrice() * request.getQuantity());

        Cart updatedCart = cartRepository.save(cart);

        return ResponseEntity.ok(new CommonResponse(HttpStatus.OK.value(), "Cart item updated successfully", updatedCart));
    }

    @Override
    public ResponseEntity<CommonResponse> removeCartItem(Long userId, Long itemId) {
        Boolean isUserExists = (Boolean) rabbitTemplate.convertSendAndReceive(exchange, routingKey, userId);
        if (Boolean.FALSE.equals(isUserExists)) {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }

        Cart cart = cartRepository.findByIdAndUserId(itemId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));

        cartRepository.delete(cart);

        return ResponseEntity.ok(new CommonResponse(HttpStatus.OK.value(), "Cart item removed successfully", null));
    }}