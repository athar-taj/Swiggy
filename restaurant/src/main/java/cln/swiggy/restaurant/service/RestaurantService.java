package cln.swiggy.restaurant.service;

import cln.swiggy.restaurant.exception.ResourceNotFoundException;
import cln.swiggy.restaurant.filter.request.RestaurantFilterRequest;
import cln.swiggy.restaurant.model.request.RestaurantRequest;
import cln.swiggy.restaurant.model.response.CommonResponse;
import org.springframework.http.ResponseEntity;

public interface RestaurantService {
    ResponseEntity<CommonResponse> createRestaurant(RestaurantRequest request) throws ResourceNotFoundException;
    ResponseEntity<CommonResponse> getAllRestaurants();
    ResponseEntity<CommonResponse> getRestaurantById(Long restaurantId)throws ResourceNotFoundException;
    ResponseEntity<CommonResponse> updateRestaurant(Long restaurantId, RestaurantRequest request)throws ResourceNotFoundException;
    ResponseEntity<CommonResponse> deleteRestaurant(Long restaurantId)throws ResourceNotFoundException;
    ResponseEntity<CommonResponse> getRestaurantAvailability(Long restaurantId);
    ResponseEntity<CommonResponse> updateRestaurantAvailability(Long restaurantId, Boolean available);
    ResponseEntity<CommonResponse> getRestaurantOutlets(String restaurantName) throws ResourceNotFoundException;
    ResponseEntity<CommonResponse> getRestaurantCategories(Long restaurantId) throws ResourceNotFoundException;
    ResponseEntity<CommonResponse> getRestaurantMenus(Long restaurantId) throws ResourceNotFoundException;
    ResponseEntity<CommonResponse> nearestRestaurants(Double lat, Double lng, Integer radius);
    ResponseEntity<CommonResponse> newlyRegisteredRestaurants(Integer limit);
    ResponseEntity<CommonResponse> filterRestaurants(RestaurantFilterRequest request);
    ResponseEntity<CommonResponse> searchRestaurants(String keyword);
    ResponseEntity<CommonResponse> nearestRestaurantsWithOffers(Double lat, Double lng, Integer radius);
}