package cln.swiggy.partner.model.request;

import cln.swiggy.partner.model.enums.MenuType;
import lombok.Data;

import java.util.List;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ComboOfferRequest {

    @NotNull(message = "Restaurant ID cannot be null")
    private Long restaurantId;

    @NotEmpty(message = "Combo name cannot be empty")
    @Size(max = 100, message = "Combo name can have a maximum of 100 characters")
    private String name;

    @Size(max = 500, message = "Description can have a maximum of 500 characters")
    private String description;

    @NotNull(message = "Price cannot be null")
    @Min(value = 0, message = "Price must be greater than or equal to 0")
    private Double price;

    @NotEmpty(message = "Item IDs cannot be empty")
    private List<Long> itemIds;

    @NotEmpty(message = "Offer IDs cannot be empty")
    private List<Long> offerIds;

    @NotNull(message = "Combo type cannot be null")
    private MenuType comboType;

    @NotEmpty(message = "Combo images cannot be empty")
    @Size(min = 2, max = 5, message = "Combo images must have at least 2 and a maximum of 5 images")
    private List<MultipartFile> images;

}