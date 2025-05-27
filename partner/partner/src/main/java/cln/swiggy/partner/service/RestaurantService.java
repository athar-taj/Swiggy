package cln.swiggy.partner.service;

import cln.swiggy.partner.exception.ResourceNotFoundException;
import cln.swiggy.partner.model.request.RestaurantRequest;
import cln.swiggy.partner.model.response.CommonResponse;
import org.springframework.http.ResponseEntity;

public interface RestaurantService {
    ResponseEntity<CommonResponse> createRestaurant(RestaurantRequest request) throws ResourceNotFoundException;
    ResponseEntity<CommonResponse> getRestaurantById(Long restaurantId)throws ResourceNotFoundException;
    ResponseEntity<CommonResponse> updateRestaurant(Long restaurantId, RestaurantRequest request)throws ResourceNotFoundException;
    ResponseEntity<CommonResponse> deleteRestaurant(Long restaurantId)throws ResourceNotFoundException;
    ResponseEntity<CommonResponse> getRestaurantAvailability(Long restaurantId);
    ResponseEntity<CommonResponse> updateRestaurantAvailability(Long restaurantId, Boolean available);
    ResponseEntity<CommonResponse> getRestaurantOutlets(String restaurantName) throws ResourceNotFoundException;
    ResponseEntity<CommonResponse> getRestaurantCategories(Long restaurantId) throws ResourceNotFoundException;
    ResponseEntity<CommonResponse> getRestaurantMenus(Long restaurantId) throws ResourceNotFoundException;
}