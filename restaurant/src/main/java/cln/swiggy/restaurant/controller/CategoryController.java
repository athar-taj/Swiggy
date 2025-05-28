package cln.swiggy.restaurant.controller;

import cln.swiggy.restaurant.model.request.CategoryRequest;
import cln.swiggy.restaurant.model.response.CommonResponse;
import cln.swiggy.restaurant.service.CategoryService;
import cln.swiggy.restaurant.serviceImpl.otherImple.ValidationUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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

    @Operation(summary = "Get all categories",
            description = "Retrieves all available categories")
    @ApiResponse(responseCode = "200", description = "Categories retrieved successfully",
            content = @Content(schema = @Schema(implementation = CommonResponse.class)))
    @GetMapping
    public ResponseEntity<CommonResponse> getAllCategories() {
        return categoryService.getAllCategories();
    }


    @Operation(
            summary = "Get top categories",
            description = "Retrieves the most popular restaurant categories based on the specified limit"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved top categories"),
            @ApiResponse(responseCode = "400", description = "Invalid limit parameter")
    })
    @GetMapping("/top")
    public ResponseEntity<CommonResponse> getTopCategories(
            @Parameter(
                    description = "Number of top categories to retrieve",
                    example = "5",
                    required = true
            )
            @Min(value = 1, message = "Limit must be at least 1")
            @RequestParam(name = "limit") int limit
    ) {
        return categoryService.getTopCategories(limit);
    }
}

