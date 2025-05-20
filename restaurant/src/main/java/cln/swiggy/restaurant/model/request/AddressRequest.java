package cln.swiggy.restaurant.model.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class AddressRequest {

    @NotNull(message = "Restaurant id is required")
    @Min(value = 1, message = "Restaurant id must be greater than 0")
    private Long restaurantId;

    @Size(max = 100, message = "Outlet name must not exceed 100 characters")
    private String outlet;

    @NotNull(message = "Latitude is required")
    @DecimalMin(value = "-90.0", message = "Latitude must be greater than or equal to -90")
    @DecimalMax(value = "90.0", message = "Latitude must be less than or equal to 90")
    private Double latitude;

    @NotNull(message = "Longitude is required")
    @DecimalMin(value = "-180.0", message = "Longitude must be greater than or equal to -180")
    @DecimalMax(value = "180.0", message = "Longitude must be less than or equal to 180")
    private Double longitude;

    @NotBlank(message = "Address is required")
    @Size(min = 5, max = 255, message = "Address must be between 5 and 255 characters")
    private String address;

    @NotBlank(message = "City is required")
    @Size(min = 2, max = 100, message = "City name must be between 2 and 100 characters")
    @Pattern(regexp = "^[a-zA-Z\\s-]+$", message = "City name can only contain letters, spaces, and hyphens")
    private String city;

    @Size(max = 100, message = "State name must not exceed 100 characters")
    @Pattern(regexp = "^[a-zA-Z\\s-]*$", message = "State name can only contain letters, spaces, and hyphens")
    private String state;

    @NotBlank(message = "Pincode is required")
    @Pattern(regexp = "^[0-9]{6}$", message = "Pincode must be 6 digits")
    private String pincode;
}