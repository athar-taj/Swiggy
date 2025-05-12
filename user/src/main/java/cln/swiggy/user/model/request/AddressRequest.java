package cln.swiggy.user.model.request;

import cln.swiggy.user.model.enums.AddressType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddressRequest {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Address type is required")
    @Enumerated(value = jakarta.persistence.EnumType.STRING)
    private AddressType addressType;

    @NotBlank(message = "Address is required")
    @Size(min = 5, max = 255, message = "Address must be between 5 and 255 characters")
    private String address;

    @NotBlank(message = "City is required")
    @Pattern(regexp = "^[a-zA-Z\\s]{2,50}$", message = "City name must contain only letters and spaces")
    private String city;

    @NotBlank(message = "State is required")
    @Pattern(regexp = "^[a-zA-Z\\s]{2,50}$", message = "State name must contain only letters and spaces")
    private String state;

    @NotBlank(message = "Pincode is required")
    @Pattern(regexp = "^[1-9][0-9]{5}$", message = "Please enter a valid 6-digit pincode")
    private String pincode;
}