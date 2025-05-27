package cln.swiggy.restaurant.model.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class MenuRequest {

    @NotNull(message = "Restaurant id is required")
    @Min(value = 1, message = "Restaurant id must be greater than 0")
    private Long restaurantId;

    @NotNull(message = "Category id is required")
    @Min(value = 1, message = "Category id must be greater than 0")
    private Long categoryId;

    @NotBlank(message = "Menu item name is required")
    @Size(min = 2, max = 100, message = "Menu item name must be between 2 and 100 characters")
    private String name;

    @Size(max = 500, message = "Description must not more then 500 characters")
    private String description;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "1", message = "Price must be greater than 1")
    private int price;

    @DecimalMin(value = "0", message = "Discount cannot be negative")
    @DecimalMax(value = "100", message = "Discount cannot more then 100%")
    private int discount  = 0;

    @NotNull(message = "Menu images are required")
    @Size(max = 5, message = "Maximum 5 images allowed")
    private List<MultipartFile> images;

}
