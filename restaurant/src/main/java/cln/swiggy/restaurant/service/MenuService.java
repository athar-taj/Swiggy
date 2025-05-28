package cln.swiggy.restaurant.service;

import cln.swiggy.restaurant.exception.ResourceNotFoundException;
import cln.swiggy.restaurant.filter.request.MenuFilterRequest;
import cln.swiggy.restaurant.filter.request.RestaurantFilterRequest;
import cln.swiggy.restaurant.model.request.MenuRequest;
import cln.swiggy.restaurant.model.response.CommonResponse;
import org.springframework.http.ResponseEntity;

public interface MenuService {
    ResponseEntity<CommonResponse> getMenuItem(Long menuItemId) throws ResourceNotFoundException;
    ResponseEntity<CommonResponse> getRestaurantMenuItems(Long restaurantId) throws ResourceNotFoundException;
    ResponseEntity<CommonResponse> filterMenu(MenuFilterRequest request);
    ResponseEntity<CommonResponse> searchMenuItems(String keyword);
}
