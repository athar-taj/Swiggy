package cln.swiggy.partner.model.request;

import cln.swiggy.partner.model.enums.OfferType;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OfferRequest {
    @NotNull(message = "Restaurant ID is required")
    private Long restaurantId;

    @NotBlank(message = "Offer name is required")
    @Size(min = 3, max = 100, message = "Offer name must be between 3 and 100 characters")
    private String offerName;

    @NotBlank(message = "Offer description is required")
    @Size(min = 10, max = 100, message = "Offer description must be between 10 and 100 characters")
    private String offerDescription;

    @NotNull(message = "Offer discount is required")
    @Min(value = 0, message = "Discount cannot be negative")
    @Max(value = 100, message = "Discount cannot be more than 100%")
    private Double offerDiscount;

    @NotNull(message = "Offer type is required")
    private OfferType offerType;

    @NotNull(message = "Start date is required")
    @Future(message = "Start date must be in the future")
    private LocalDateTime startDate;

    @NotNull(message = "End date is required")
    @Future(message = "End date must be in the future")
    private LocalDateTime endDate;

    @NotNull(message = "Active status is required")
    private Boolean isActive;

    @Min(value = 0, message = "Minimum order value cannot be negative")
    private Double minimumOrderValue;

    @Min(value = 0, message = "Maximum discount amount cannot be negative")
    private Double maximumDiscountAmount;

    @Pattern(regexp = "^[A-Z0-9]{4,10}$", message = "Offer code must be 4-10 characters long and contain only uppercase letters and numbers")
    private String offerCode;

    @Size(max = 200, message = "Terms and conditions cannot exceed 200 characters")
    private String termsAndConditions;

    @Pattern(regexp = "^(MON|TUE|WED|THU|FRI|SAT|SUN)(,(MON|TUE|WED|THU|FRI|SAT|SUN))*$",
            message = "Applicable days should be comma-separated values of MON,TUE,WED,THU,FRI,SAT,SUN")
    private String applicableDays;

    @AssertTrue(message = "End date must be after start date")
    private boolean isEndDateAfterStartDate() {
        if (startDate == null || endDate == null) {
            return true;
        }
        return endDate.isAfter(startDate);
    }

    @AssertTrue(message = "Maximum discount amount must be greater than minimum order value")
    private boolean isMaxDiscountValid() {
        if (maximumDiscountAmount == null || minimumOrderValue == null) {
            return true;
        }
        return maximumDiscountAmount >= minimumOrderValue;
    }
}