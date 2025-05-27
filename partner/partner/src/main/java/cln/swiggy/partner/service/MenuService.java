package cln.swiggy.partner.service;

import cln.swiggy.partner.exception.ResourceNotFoundException;
import cln.swiggy.partner.model.request.MenuRequest;
import cln.swiggy.partner.model.response.CommonResponse;
import org.springframework.http.ResponseEntity;

public interface MenuService {
    ResponseEntity<CommonResponse> createMenuItem(Long restaurantId, MenuRequest request) throws ResourceNotFoundException;
    ResponseEntity<CommonResponse> getMenuItem(Long menuItemId) throws ResourceNotFoundException;
    ResponseEntity<CommonResponse> getRestaurantMenuItems(Long restaurantId) throws ResourceNotFoundException;
    ResponseEntity<CommonResponse> updateMenuItem(Long menuItemId, MenuRequest request) throws ResourceNotFoundException;
    ResponseEntity<CommonResponse> deleteMenuItem(Long menuItemId) throws ResourceNotFoundException;
}
