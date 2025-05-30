package cln.swiggy.rating.controller;

import cln.swiggy.rating.model.request.RatingRequest;
import cln.swiggy.rating.model.response.CommonResponse;
import cln.swiggy.rating.service.RestaurantService;
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
@RequestMapping("/api/restaurants-rating")
@Tag(name = "Restaurant Rating Controller",
        description = "Endpoints for managing restaurant ratings and reviews")
public class RestaurantRatingController {

    @Autowired
    RestaurantService restaurantService;

    @Operation(summary = "Get rating details",
            description = "Retrieves a specific restaurant rating by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Rating found and returned successfully",
                    content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "404", description = "Rating not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{ratingId}")
    public ResponseEntity<CommonResponse> getRating(
            @Parameter(description = "ID of the rating to retrieve", required = true)
            @PathVariable Long ratingId) {
        return restaurantService.getRatingById(ratingId);
    }

    @Operation(summary = "Create restaurant rating",
            description = "Creates a new rating for a specific restaurant")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Rating created successfully",
                    content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Restaurant not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/{restaurantId}/ratings")
    public ResponseEntity<CommonResponse> createRating(
            @Parameter(description = "ID of the restaurant to rate", required = true)
            @PathVariable Long restaurantId,
            @Parameter(description = "Rating details", required = true)
            @Valid @RequestBody RatingRequest request) {
        request.setElementId(restaurantId);
        return restaurantService.createRating(request);
    }

    @Operation(summary = "Update rating",
            description = "Updates an existing restaurant rating")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Rating updated successfully",
                    content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Rating not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Cannot modify other user's rating"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{ratingId}")
    public ResponseEntity<CommonResponse> updateRating(
            @Parameter(description = "ID of the rating to update", required = true)
            @PathVariable Long ratingId,
            @Parameter(description = "Updated rating details", required = true)
            @Valid @RequestBody RatingRequest request) {
        return restaurantService.updateRating(ratingId, request);
    }

    @Operation(summary = "Get user's restaurant ratings",
            description = "Retrieves all restaurant ratings submitted by a specific user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ratings retrieved successfully",
                    content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<CommonResponse> getUserRatings(
            @Parameter(description = "ID of the user whose ratings to retrieve", required = true)
            @PathVariable Long userId) {
        return restaurantService.getRatingsByUserId(userId);
    }
}