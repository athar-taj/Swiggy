package cln.swiggy.restaurant.service;


import cln.swiggy.restaurant.model.request.CartRequest;
import cln.swiggy.restaurant.model.response.CommonResponse;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;

public interface CartService {
    ResponseEntity<CommonResponse> getCartItems(Long userId);
    ResponseEntity<CommonResponse> addItemToCart(Long userId, CartRequest request) throws BadRequestException;
    ResponseEntity<CommonResponse> updateCartItem(Long userId, Long itemId, CartRequest request) throws BadRequestException;
    ResponseEntity<CommonResponse> removeCartItem(Long userId, Long itemId);
}
