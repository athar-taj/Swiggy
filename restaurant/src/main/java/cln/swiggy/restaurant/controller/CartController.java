
package cln.swiggy.restaurant.controller;

import cln.swiggy.restaurant.model.request.CartRequest;
import cln.swiggy.restaurant.model.response.CommonResponse;
import cln.swiggy.restaurant.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@Tag(name = "Shopping Cart", description = "APIs for managing user shopping cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Operation(summary = "Get cart items",
            description = "Retrieves all items in a user's shopping cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cart items retrieved successfully",
                    content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{userId}/items")
    public ResponseEntity<CommonResponse> getCartItems(
            @Parameter(description = "ID of the user", required = true)
            @PathVariable Long userId) {
        return cartService.getCartItems(userId);
    }

    @Operation(summary = "Add item to cart",
            description = "Adds a new item to user's shopping cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item added successfully",
                    content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PostMapping("/{userId}/items")
    public ResponseEntity<CommonResponse> addItemToCart(
            @Parameter(description = "ID of the user", required = true)
            @PathVariable Long userId,
            @Parameter(description = "Cart item details", required = true)
            @Valid @RequestBody CartRequest request) throws BadRequestException {
        return cartService.addItemToCart(userId, request);
    }

    @Operation(summary = "Update cart item",
            description = "Updates quantity or other details of an item in the cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cart item updated successfully",
                    content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "User or cart item not found")
    })
    @PutMapping("/{userId}/items/{itemId}")
    public ResponseEntity<CommonResponse> updateCartItem(
            @Parameter(description = "ID of the user", required = true)
            @PathVariable Long userId,
            @Parameter(description = "ID of the cart item", required = true)
            @PathVariable Long itemId,
            @Parameter(description = "Updated cart item details", required = true)
            @Valid @RequestBody CartRequest request) throws BadRequestException {
        return cartService.updateCartItem(userId, itemId, request);
    }

    @Operation(summary = "Remove cart item",
            description = "Removes an item from the user's shopping cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cart item removed successfully",
                    content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "404", description = "User or cart item not found")
    })
    @DeleteMapping("/{userId}/items/{itemId}")
    public ResponseEntity<CommonResponse> removeCartItem(
            @Parameter(description = "ID of the user", required = true)
            @PathVariable Long userId,
            @Parameter(description = "ID of the cart item to remove", required = true)
            @PathVariable Long itemId) {
        return cartService.removeCartItem(userId, itemId);
    }
}