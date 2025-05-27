package cln.swiggy.partner.model.request;

import cln.swiggy.partner.model.enums.RestaurantType;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalTime;
import java.util.List;

@Data
public class RestaurantRequest {

    @NotNull(message = "Owner id is required")
    @Min(value = 1, message = "Owner id must be greater than 0")
    private Long ownerId;

    @NotBlank(message = "Restaurant name is required")
    @Size(min = 2, max = 100, message = "Restaurant name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Contact number is required")
    @Pattern(regexp = "^\\d{10}$", message = "Invalid contact number")
    private String contactNo;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Outlet location is required")
    private String outlet;

    @Size(max = 500, message = "Description must not more then 500 characters")
    private String description;

    @NotNull(message = "Restaurant type is required")
    private RestaurantType restaurantType;

    @NotEmpty(message = "At least one opening day must be selected")
    @Size(max = 7, message = "Maximum 7 days can be selected")
    private List<@Pattern(
            regexp = "^(MONDAY|TUESDAY|WEDNESDAY|THURSDAY|FRIDAY|SATURDAY|SUNDAY)$",
            message = "Invalid day format"
    ) String> openDays;

    @NotNull(message = "Start time is required")
    private LocalTime startTime;

    @NotNull(message = "End time is required")
    private LocalTime endTime;

    @NotNull(message = "Average Delivery Time is Required")
    private LocalTime avgDeliveryTime;

    @Min(value = 0 , message = "Cost can't be Negative")
    private Double costForTwo;

    private MultipartFile logo;

    @Size(max = 5, message = "Minimum 5 restaurant images are required")
    private List<MultipartFile> images;

    @NotEmpty(message = "At least one category must be selected")
    private List<Long> categoryIds;

    @AssertTrue(message = "End time must be after start time")
    private boolean isValidTimeRange() {
        if (startTime == null || endTime == null) {
            return true;
        }
        return endTime.isAfter(startTime);
    }

    @AssertTrue(message = "At least one day must be selected")
    private boolean isValidOpenDays() {
        return openDays != null && !openDays.isEmpty() && openDays.size() <= 7;
    }

    @AssertTrue(message = "Invalid Logo format. Allowed formats are: JPG, JPEG, PNG")
    private boolean isValidImageFormat() {
        if (logo == null || logo.isEmpty()) {
            return true;
        }
        String contentType = logo.getContentType();
        return contentType != null && (
                contentType.equals("image/jpeg") ||
                        contentType.equals("image/jpg") ||
                        contentType.equals("image/png")
        );
    }

    @AssertTrue(message = "Logo size should not exceed 2MB")
    private boolean isValidImageSize() {
        if (logo == null || logo.isEmpty()) {
            return true;
        }
        return logo.getSize() <= 2 * 1024 * 1024;
    }
}