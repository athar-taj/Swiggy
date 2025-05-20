package cln.swiggy.rating.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FeedbackRequest {
    @NotNull(message = "User ID cannot be null")
    private Long userId;

    @NotNull(message = "Restaurant ID cannot be null")
    private Long restaurantId;

    @NotBlank(message = "Feedback is Required")
    private String feedback;
}