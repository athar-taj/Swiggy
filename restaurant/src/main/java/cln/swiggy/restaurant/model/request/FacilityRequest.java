package cln.swiggy.restaurant.model.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class FacilityRequest {

    @NotNull(message = "Restaurant id is required")
    @Min(value = 1, message = "Restaurant id must be greater than 0")
    private Long restaurantId;

    @NotBlank(message = "Facility name is required")
    @Size(min = 2, max = 100, message = "Facility name must be between 2 and 100 characters")
    private String facilityName;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;
}
