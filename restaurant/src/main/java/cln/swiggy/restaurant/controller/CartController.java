package cln.swiggy.restaurant.controller;

import cln.swiggy.restaurant.model.request.CartRequest;
import cln.swiggy.restaurant.model.response.CommonResponse;
import cln.swiggy.restaurant.service.CartService;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/{userId}/items")
    public ResponseEntity<CommonResponse> getCartItems(
            @PathVariable  Long userId) {
        return cartService.getCartItems(userId);
    }

    @PostMapping("/{userId}/items")
    public ResponseEntity<CommonResponse> addItemToCart(
            @PathVariable  Long userId,
            @Valid @RequestBody CartRequest request) throws BadRequestException {
        return cartService.addItemToCart(userId, request);
    }

    @PutMapping("/{userId}/items/{itemId}")
    public ResponseEntity<CommonResponse> updateCartItem(
            @PathVariable Long userId,
            @PathVariable Long itemId,
            @Valid @RequestBody CartRequest request) throws BadRequestException {
        return cartService.updateCartItem(userId, itemId, request);
    }

    @DeleteMapping("/{userId}/items/{itemId}")
    public ResponseEntity<CommonResponse> removeCartItem(
            @PathVariable Long userId,
            @PathVariable Long itemId) {
        return cartService.removeCartItem(userId, itemId);
    }
}
