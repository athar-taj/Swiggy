package cln.swiggy.restaurant.controller;

import cln.swiggy.restaurant.model.request.RestaurantMenuImageRequest;
import cln.swiggy.restaurant.model.response.CommonResponse;
import cln.swiggy.restaurant.service.RestaurantMenuImageService;
import cln.swiggy.restaurant.serviceImpl.otherImple.ImageValidation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants/menu-images")
@Tag(name = "Restaurant Menu Images", description = "Endpoints for managing restaurant menu images")
public class RestaurantMenuImageController {

    @Autowired
    private RestaurantMenuImageService menuImageService;

    @Operation(
        summary = "Add menu images",
        description = "Add one or more images to a restaurant's menu. Supports JPG, JPEG, PNG formats up to 2MB each."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Images added successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid image format or size"),
        @ApiResponse(responseCode = "404", description = "Restaurant not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CommonResponse> addMenuImages(
        @Parameter(description = "Restaurant ID and image files", required = true)
        @ModelAttribute RestaurantMenuImageRequest request
    ) {
        List<String> validationErrors = ImageValidation.validateImages(request.getImages());
        if (validationErrors != null && !validationErrors.isEmpty()) {
            return ResponseEntity.badRequest()
                .body(new CommonResponse(400, "Image Validation failed", validationErrors));
        }
        return menuImageService.addMenuImages(request);
    }

    @Operation(
        summary = "Update menu image",
        description = "Update a specific menu image. Supports JPG, JPEG, PNG formats up to 2MB."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Image updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid image format or size"),
        @ApiResponse(responseCode = "404", description = "Image or restaurant not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping(value = "/{imageId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CommonResponse> updateMenuImage(
        @Parameter(description = "ID of the image to update", required = true)
        @PathVariable Long imageId,
        @Parameter(description = "New image file", required = true)
        @RequestParam("image") MultipartFile image
    ) {
        String validationError = ImageValidation.validateImage(image);
        if (validationError != null) {
            return ResponseEntity.badRequest()
                .body(new CommonResponse(400, "Image Validation failed", validationError));
        }
        return menuImageService.updateMenuImage(imageId, image);
    }

    @Operation(
        summary = "Get menu images",
        description = "Retrieve all menu images for a specific restaurant"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Images retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Restaurant not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{restaurantId}")
    public ResponseEntity<CommonResponse> getMenuImages(
        @Parameter(description = "ID of the restaurant", required = true)
        @PathVariable Long restaurantId
    ) {
        return menuImageService.getMenuImages(restaurantId);
    }

    @Operation(
        summary = "Delete menu image",
        description = "Delete a specific menu image"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Image deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Image or restaurant not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{imageId}")
    public ResponseEntity<CommonResponse> deleteMenuImage(
        @Parameter(description = "ID of the image to delete", required = true)
        @PathVariable Long imageId
    ) {
        return menuImageService.deleteMenuImage(imageId);
    }
}