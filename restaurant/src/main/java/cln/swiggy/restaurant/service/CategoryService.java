package cln.swiggy.restaurant.service;

import cln.swiggy.restaurant.model.request.CategoryRequest;
import cln.swiggy.restaurant.model.response.CommonResponse;
import org.springframework.http.ResponseEntity;

import java.io.IOException;


public interface CategoryService {
    ResponseEntity<CommonResponse> createCategory(CategoryRequest request) throws IOException;
    ResponseEntity<CommonResponse> getAllCategories();
    ResponseEntity<CommonResponse> getCategoryById(Long id);
    ResponseEntity<CommonResponse> updateCategory(Long id, CategoryRequest request) throws IOException;
    ResponseEntity<CommonResponse> deleteCategory(Long id) throws IOException;
    ResponseEntity<CommonResponse> removeCategoryFromRestaurant(Long categoryId, Long restaurantId);
    ResponseEntity<CommonResponse> getTopCategories(int limit);
}
