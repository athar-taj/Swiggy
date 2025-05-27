package cln.swiggy.rating.controller;

import cln.swiggy.rating.model.request.RatingRequest;
import cln.swiggy.rating.model.response.CommonResponse;
import cln.swiggy.rating.service.MenuService;
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
@RequestMapping("/api/menu-ratings")
@Tag(name = "Menu Rating Controller", description = "Endpoints for managing menu item ratings")
public class MenuRatingController {
    @Autowired
    MenuService menuService;

    @Operation(summary = "Get rating by ID",
            description = "Retrieves a specific menu item rating by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Rating found and returned",
                    content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "404", description = "Rating not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{ratingId}")
    public ResponseEntity<CommonResponse> getRating(
            @Parameter(description = "ID of the rating to retrieve", required = true)
            @PathVariable Long ratingId) {
        return menuService.getRatingById(ratingId);
    }

    @Operation(summary = "Create new rating",
            description = "Creates a new rating for a menu item")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Rating created successfully",
                    content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<CommonResponse> createRating(
            @Parameter(description = "Rating details", required = true)
            @Valid @RequestBody RatingRequest request) {
        return menuService.createRating(request);
    }

    @Operation(summary = "Update existing rating",
            description = "Updates an existing menu item rating")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Rating updated successfully",
                    content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Rating not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{ratingId}")
    public ResponseEntity<CommonResponse> updateRating(
            @Parameter(description = "ID of the rating to update", required = true)
            @PathVariable Long ratingId,
            @Parameter(description = "Updated rating details", required = true)
            @Valid @RequestBody RatingRequest request) {
        return menuService.updateRating(ratingId, request);
    }

    @Operation(summary = "Get user's ratings",
            description = "Retrieves all ratings submitted by a specific user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ratings retrieved successfully",
                    content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<CommonResponse> getUserRatings(
            @Parameter(description = "ID of the user", required = true)
            @PathVariable Long userId) {
        return menuService.getRatingsByUserId(userId);
    }
}