package cln.swiggy.rating.model.request;

import cln.swiggy.rating.model.enums.RatingFor;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RatingRequest {
    @NotNull(message = "User ID cannot be null")
    private Long userId;

    @NotNull(message = "Restaurant/Item ID cannot be null")
    private Long elementId;

    @NotNull(message = "Rating cannot be null")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating cannot be more than 5")
    private int rating;

    @NotBlank(message = "Comment cannot be blank")
    @Size(min = 10, max = 500, message = "Comment must be between 10 and 500 characters")
    private String comment;
}