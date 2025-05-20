package cln.swiggy.restaurant.model.response;

import cln.swiggy.restaurant.model.Cart;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class CartResponse {

    private Long userId;
    private String menuName;
    private Double menuPrice;
    private Integer quantity;
    private Double totalPrice;

    public static CartResponse convertToCartResponse(Cart cart) {
        CartResponse response = new CartResponse();

        response.setMenuName(cart.getCartMenu().getName());
        response.setMenuPrice(cart.getPrice());
        response.setQuantity(cart.getQuantity());
        response.setTotalPrice(cart.getTotalPrice());

        return response;
    }

    public static List<CartResponse> convertToCartResponseWithMenu(List<Cart> carts) {
        return carts.stream()
                .map(CartResponse::convertToCartResponse)
                .collect(Collectors.toList());
    }

}
