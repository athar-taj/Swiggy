package cln.swiggy.restaurant.service;

import cln.swiggy.restaurant.model.request.WishlistRequest;
import cln.swiggy.restaurant.model.response.CommonResponse;
import org.springframework.http.ResponseEntity;

public interface WishlistService {
    ResponseEntity<CommonResponse> addToWishlist(Long userId, WishlistRequest request);
    ResponseEntity<CommonResponse> removeFromWishlist(Long userId, Long itemId);
    ResponseEntity<CommonResponse> getWishlistItems(Long userId);
}
