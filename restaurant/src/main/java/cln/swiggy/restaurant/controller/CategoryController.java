package cln.swiggy.restaurant.controller;

import cln.swiggy.restaurant.model.request.CategoryRequest;
import cln.swiggy.restaurant.model.response.CommonResponse;
import cln.swiggy.restaurant.service.CategoryService;
import cln.swiggy.restaurant.serviceImpl.otherImple.ValidationUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;
    @Autowired
    ValidationUtil validationUtil;

    @PostMapping(
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> createCategory(
            @RequestPart(value = "images", required = true) MultipartFile image,
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

    @GetMapping
    public ResponseEntity<CommonResponse> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @DeleteMapping("/restaurant")
    public ResponseEntity<CommonResponse> removeCategory(@RequestParam("categoryId") Long categoryId,@RequestParam("restaurantId") Long restaurantId) throws IOException {
        return categoryService.removeCategoryFromRestaurant(categoryId, restaurantId);
    }

    @PutMapping(value = "/{id}",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateCategory(
            @PathVariable Long id,
            @RequestPart(value = "images", required = false) MultipartFile image,
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

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse> deleteCategory(@PathVariable Long id) throws IOException {
        return categoryService.deleteCategory(id);
    }
}
