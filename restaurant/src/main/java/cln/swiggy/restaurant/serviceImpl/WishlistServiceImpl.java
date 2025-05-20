package cln.swiggy.restaurant.serviceImpl;

import cln.swiggy.restaurant.exception.ResourceNotFoundException;
import cln.swiggy.restaurant.model.Menu;
import cln.swiggy.restaurant.model.Wishlist;
import cln.swiggy.restaurant.model.request.WishlistRequest;
import cln.swiggy.restaurant.model.response.CommonResponse;
import cln.swiggy.restaurant.model.response.WishlistResponse;
import cln.swiggy.restaurant.repository.MenuRepository;
import cln.swiggy.restaurant.repository.WishlistRepository;
import cln.swiggy.restaurant.service.WishlistService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WishlistServiceImpl implements WishlistService {

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.routing-key}")
    private String routingKey;

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;


    @Override
    public ResponseEntity<CommonResponse> addToWishlist(Long userId, WishlistRequest request) {
        Boolean isUserExists = (Boolean) rabbitTemplate.convertSendAndReceive(exchange, routingKey, userId);
        if (Boolean.FALSE.equals(isUserExists)) {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }

        Menu menu = menuRepository.findById(request.getMenuId())
                .orElseThrow(() -> new ResourceNotFoundException("Menu not found with id: " + request.getMenuId()));

        if (wishlistRepository.existsByUserIdAndMenu(userId, menu)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new CommonResponse(HttpStatus.BAD_REQUEST.value(), "Item already exists in wishlist", null));
        }

        Wishlist wishlist = new Wishlist();
        wishlist.setUserId(userId);
        wishlist.setMenu(menu);

        Wishlist savedWishlist = wishlistRepository.save(wishlist);
        WishlistResponse response = WishlistResponse.fromEntity(savedWishlist);

        return ResponseEntity.ok(new CommonResponse(HttpStatus.OK.value(), "Item added to wishlist successfully", response));
    }

    @Override
    public ResponseEntity<CommonResponse> getWishlistItems(Long userId) {
        Boolean isUserExists = (Boolean) rabbitTemplate.convertSendAndReceive(exchange, routingKey, userId);
        if (Boolean.FALSE.equals(isUserExists)) {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }

        List<Wishlist> wishlistItems = wishlistRepository.findByUserId(userId);
        List<WishlistResponse> responses = wishlistItems.stream()
                .map(WishlistResponse::fromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new CommonResponse(HttpStatus.OK.value(), "Wishlist items retrieved successfully", responses));
    }

    @Override
    public ResponseEntity<CommonResponse> removeFromWishlist(Long userId, Long itemId) {
        Boolean isUserExists = (Boolean) rabbitTemplate.convertSendAndReceive(exchange, routingKey, userId);
        if (Boolean.FALSE.equals(isUserExists)) {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }

        Wishlist wishlist = wishlistRepository.findByIdAndUserId(itemId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Wishlist item not found"));

        wishlistRepository.delete(wishlist);
        return ResponseEntity.ok(new CommonResponse(HttpStatus.OK.value(), "Item removed from wishlist successfully", null));
    }
}