package cln.swiggy.rating.controller;

import cln.swiggy.rating.model.request.FeedbackRequest;
import cln.swiggy.rating.model.response.CommonResponse;
import cln.swiggy.rating.service.FeedbackService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/feedbacks")
@Tag(name = "Feedback Controller", description = "Managing customer feedbacks for restaurant")
public class FeedbackController {

    @Autowired
    FeedbackService feedbackService;

    @Operation(summary = "Create new feedback",
            description = "Creates a new feedback entry from a customer")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Feedback created successfully",
                    content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<CommonResponse> createFeedback(
            @Parameter(description = "Feedback request body", required = true)
            @Valid @RequestBody FeedbackRequest request) {
        return feedbackService.createFeedback(request);
    }

    @Operation(summary = "Get restaurant feedbacks",
            description = "Retrieves all feedbacks for a specific restaurant")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Feedbacks retrieved successfully",
                    content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "404", description = "Restaurant not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<CommonResponse> getFeedbacksByRestaurant(
            @Parameter(description = "ID of the restaurant", required = true)
            @PathVariable Long restaurantId) {
        return feedbackService.getFeedbacksByRestaurant(restaurantId);
    }

    @Operation(summary = "Get user feedbacks",
            description = "Retrieves all feedbacks submitted by a specific user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Feedbacks retrieved successfully",
                    content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<CommonResponse> getFeedbacksByUser(
            @Parameter(description = "ID of the user", required = true)
            @PathVariable Long userId) {
        return feedbackService.getFeedbacksByUser(userId);
    }
}