package cln.swiggy.restaurant.controller;

import cln.swiggy.restaurant.model.request.WishlistRequest;
import cln.swiggy.restaurant.model.response.CommonResponse;
import cln.swiggy.restaurant.service.WishlistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wishlist")
@Tag(name = "Wishlist", description = "Endpoints for managing user wishlists")
public class WishlistController {

    @Autowired
    WishlistService wishlistService;

    @Operation(
            summary = "Add item to wishlist",
            description = "Adds a new item to a user's wishlist"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item added to wishlist successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/{userId}/items")
    public ResponseEntity<CommonResponse> addToWishlist(
            @Parameter(description = "ID of the user", required = true)
            @PathVariable Long userId,
            @Parameter(description = "Wishlist item details", required = true)
            @Valid @RequestBody WishlistRequest request) {
        return wishlistService.addToWishlist(userId, request);
    }

    @Operation(
            summary = "Remove item from wishlist",
            description = "Removes an item from a user's wishlist"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item removed from wishlist successfully"),
            @ApiResponse(responseCode = "404", description = "User or item not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{userId}/items/{itemId}")
    public ResponseEntity<CommonResponse> removeFromWishlist(
            @Parameter(description = "ID of the user", required = true)
            @PathVariable Long userId,
            @Parameter(description = "ID of the item to remove", required = true)
            @PathVariable Long itemId) {
        return wishlistService.removeFromWishlist(userId, itemId);
    }

    @Operation(
            summary = "Get wishlist items",
            description = "Retrieves all items in a user's wishlist"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved wishlist items"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{userId}/items")
    public ResponseEntity<CommonResponse> getWishlistItems(
            @Parameter(description = "ID of the user", required = true)
            @PathVariable Long userId) {
        return wishlistService.getWishlistItems(userId);
    }
}