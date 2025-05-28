package cln.swiggy.restaurant.service;

import cln.swiggy.restaurant.model.request.CategoryRequest;
import cln.swiggy.restaurant.model.response.CommonResponse;
import org.springframework.http.ResponseEntity;

import java.io.IOException;


public interface CategoryService {
    ResponseEntity<CommonResponse> getAllCategories();
    ResponseEntity<CommonResponse> getCategoryById(Long id);
    ResponseEntity<CommonResponse> getTopCategories(int limit);
}
