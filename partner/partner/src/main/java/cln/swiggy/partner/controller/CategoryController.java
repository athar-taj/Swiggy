package cln.swiggy.partner.controller;

import cln.swiggy.partner.model.request.CategoryRequest;
import cln.swiggy.partner.model.response.CommonResponse;
import cln.swiggy.partner.service.CategoryService;
import cln.swiggy.partner.serviceImpl.otherImple.ValidationUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/category")
@Tag(name = "Category Management", description = "APIs for managing restaurant categories")
public class CategoryController {

    @Autowired
    CategoryService categoryService;
    @Autowired
    ValidationUtil validationUtil;

    @Operation(summary = "Create new category",
            description = "Creates a new category with image upload support")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "415", description = "Unsupported media type")
    })
    @PostMapping(
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> createCategory(
            @Parameter(description = "Category image file", required = true)
            @RequestPart(value = "images", required = true) MultipartFile image,
            @Parameter(description = "Category details in JSON format", required = true)
            @RequestPart(value = "categoryData", required = true) String categoryDataJson
    ) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        CategoryRequest request = mapper.readValue(categoryDataJson, CategoryRequest.class);
        request.setImage(image);
        ResponseEntity<?> validationResponse = validationUtil.validateRequest(request);
        if (validationResponse != null) {
            return validationResponse;
        }
        return categoryService.createCategory(request);
    }

    @Operation(summary = "Get all categories",
            description = "Retrieves all available categories")
    @ApiResponse(responseCode = "200", description = "Categories retrieved successfully",
            content = @Content(schema = @Schema(implementation = CommonResponse.class)))
    @GetMapping
    public ResponseEntity<CommonResponse> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @Operation(summary = "Remove category from restaurant",
            description = "Removes a category association from a specific restaurant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category removed successfully"),
            @ApiResponse(responseCode = "404", description = "Category or restaurant not found")
    })
    @DeleteMapping("/restaurant")
    public ResponseEntity<CommonResponse> removeCategory(
            @Parameter(description = "ID of the category to remove", required = true)
            @RequestParam("categoryId") Long categoryId,
            @Parameter(description = "ID of the restaurant", required = true)
            @RequestParam("restaurantId") Long restaurantId) throws IOException {
        return categoryService.removeCategoryFromRestaurant(categoryId, restaurantId);
    }

    @Operation(summary = "Update category",
            description = "Updates an existing category with optional image update")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    @PutMapping(value = "/{id}",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateCategory(
            @Parameter(description = "ID of the category to update", required = true)
            @PathVariable Long id,
            @Parameter(description = "Updated category image file (optional)")
            @RequestPart(value = "images", required = false) MultipartFile image,
            @Parameter(description = "Updated category details in JSON format", required = true)
            @RequestPart(value = "categoryData", required = true) String categoryDataJson
    ) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        CategoryRequest request = mapper.readValue(categoryDataJson, CategoryRequest.class);
        request.setImage(image);
        ResponseEntity<?> validationResponse = validationUtil.validateRequest(request);
        if (validationResponse != null) {
            return validationResponse;
        }
        return categoryService.updateCategory(id, request);
    }

    @Operation(summary = "Delete category",
            description = "Permanently deletes a category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse> deleteCategory(
            @Parameter(description = "ID of the category to delete", required = true)
            @PathVariable Long id) throws IOException {
        return categoryService.deleteCategory(id);
    }
}

