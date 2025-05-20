package cln.swiggy.restaurant.controller;

import cln.swiggy.restaurant.model.request.WishlistRequest;
import cln.swiggy.restaurant.model.response.CommonResponse;
import cln.swiggy.restaurant.service.WishlistService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {

    @Autowired
    WishlistService wishlistService;

    @PostMapping("/{userId}/items")
    public ResponseEntity<CommonResponse> addToWishlist(
            @PathVariable Long userId,
            @Valid @RequestBody WishlistRequest request) {
        return wishlistService.addToWishlist(userId, request);
    }

    @DeleteMapping("/{userId}/items/{itemId}")
    public ResponseEntity<CommonResponse> removeFromWishlist(
            @PathVariable Long userId,
            @PathVariable Long itemId) {
        return wishlistService.removeFromWishlist(userId, itemId);
    }

    @GetMapping("/{userId}/items")
    public ResponseEntity<CommonResponse> getWishlistItems(
            @PathVariable Long userId) {
        return wishlistService.getWishlistItems(userId);
    }
}
