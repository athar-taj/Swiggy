package cln.swiggy.restaurant.model.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class WishlistRequest {

    @NotNull(message = "User ID is required")
    @Positive(message = "User ID must be greater than 0")
    private Long userId;

    @NotNull(message = "Menu ID is required")
    @Positive(message = "Menu ID must be greater than 0")
    private Long menuId;

}
