package cln.swiggy.restaurant.controller;

import cln.swiggy.restaurant.model.request.CategoryRequest;
import cln.swiggy.restaurant.model.response.CommonResponse;
import cln.swiggy.restaurant.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CommonResponse> createCategory(@Valid @ModelAttribute CategoryRequest request) throws IOException {
        return categoryService.createCategory(request);
    }

    @GetMapping
    public ResponseEntity<CommonResponse> getAllCategories() {
        return categoryService.getAllCategories();
    }
    @DeleteMapping("/restaurant")
    public ResponseEntity<CommonResponse> getCategory(@RequestParam("categoryId") Long categoryId,@RequestParam("restaurantId") Long restaurantId) throws IOException {
        return categoryService.removeCategoryFromRestaurant(categoryId, restaurantId);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CommonResponse> updateCategory(
            @PathVariable Long id,
            @Valid @ModelAttribute CategoryRequest request) throws IOException {
        return categoryService.updateCategory(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse> deleteCategory(@PathVariable Long id) throws IOException {
        return categoryService.deleteCategory(id);
    }
}
