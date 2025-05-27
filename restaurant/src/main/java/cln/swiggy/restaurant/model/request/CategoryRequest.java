package cln.swiggy.restaurant.model.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CategoryRequest {

    @NotBlank(message = "Category name is required")
    @Size(min = 2, max = 30, message = "Category name must be between 2 and 50 characters")
    private String name;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;

    @NotNull(message = "Category image is required")
    private MultipartFile image;

//    @NotNull(message = "Restaurant id is required")
//    @Min(value = 1, message = "Restaurant id must be greater than 0")
//    private Long restaurantId;

    @AssertTrue(message = "Invalid image format. Allowed formats are: JPG, JPEG, PNG")
    private boolean isValidImageFormat() {
        if (image == null || image.isEmpty()) {
            return true;
        }
        String contentType = image.getContentType();
        return contentType != null && (
                contentType.equals("image/jpeg") ||
                        contentType.equals("image/jpg") ||
                        contentType.equals("image/png")
        );
    }

    @AssertTrue(message = "Image size should not exceed 2MB")
    private boolean isValidImageSize() {
        if (image == null || image.isEmpty()) {
            return true;
        }
        return image.getSize() <= 2 * 1024 * 1024;
    }
}
