package cln.swiggy.restaurant.model.response;

import cln.swiggy.restaurant.model.Wishlist;
import lombok.Data;

@Data
public class WishlistResponse {
    private Long id;
    private Long userId;
    private Long menuId;
    private String menuName;
    private String menuDescription;
    private Double menuPrice;
    private Boolean isAvailable;

    public static WishlistResponse fromEntity(Wishlist wishlist) {
        WishlistResponse response = new WishlistResponse();
        response.setId(wishlist.getId());
        response.setUserId(wishlist.getUserId());
        response.setMenuId(wishlist.getMenu().getId());
        response.setMenuName(wishlist.getMenu().getName());
        response.setMenuDescription(wishlist.getMenu().getDescription());
        response.setMenuPrice(wishlist.getMenu().getPrice());
        response.setIsAvailable(wishlist.getMenu().getIsAvailable());
        return response;
    }

}