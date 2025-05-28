package cln.swiggy.order.model.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class OrderRequest {
    @NotNull(message = "User ID must not be null")
    private Long userId;

    @NotNull(message = "Menu ID must not be null")
    private Long menuId;

    @NotNull(message = "Restaurant ID must not be null")
    private Long restaurantId;

    @NotBlank(message = "Delivery address must not be empty")
    @Size(max = 255, message = "Delivery address must not exceed 255 characters")
    private String deliveryAddress;

    @Size(max = 100, message = "Delivery city must not exceed 100 characters")
    private String deliveryCity;

    @NotBlank(message = "Delivery pincode must not be empty")
    @Pattern(regexp = "^[0-9]{6}$", message = "Invalid pincode format. Must be 6 digits")
    private String deliveryPincode;

    @Size(max = 255, message = "Delivery landmark must not exceed 255 characters")
    private String deliveryLandmark;

    @NotBlank(message = "Receiver name must not be empty")
    @Size(min = 2, max = 100, message = "Receiver name must be between 2 and 100 characters")
    private String receiverName;

    @NotBlank(message = "Receiver phone number must not be empty")
    @Pattern(regexp = "^[0-9]{10}$", message = "Invalid phone number format. Must be 10 digits")
    private String receiverPhoneNumber;

    @NotNull(message = "Quantity must not be null")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    @NotBlank(message = "Delivery partner name cannot be blank")
    @Size(max = 100, message = "Delivery partner name must not exceed 100 characters")
    private String deliveryPartnerName;

    @NotBlank(message = "Delivery partner phone cannot be blank")
    @Pattern(regexp = "^\\d{10}$", message = "Delivery partner phone is not valid")
    private String deliveryPartnerPhone;

    @DecimalMin(value = "-90.0", message = "Latitude must be greater than or equal to -90")
    @DecimalMax(value = "90.0", message = "Latitude must be less than or equal to 90")
    @NotNull(message = "Latitude must not be null")
    private double currentLatitude;

    @DecimalMin(value = "-180.0", message = "Longitude must be greater than or equal to -180")
    @DecimalMax(value = "180.0", message = "Longitude must be less than or equal to 180")
    @NotNull(message = "Longitude must not be null")
    private double currentLongitude;

    @NotBlank(message = "Estimated delivery time cannot be blank")
    @Pattern(regexp = "^\\d{2}:\\d{2}$", message = "Estimated delivery time must be in HH:mm format")
    private String estimatedDeliveryTime;

}