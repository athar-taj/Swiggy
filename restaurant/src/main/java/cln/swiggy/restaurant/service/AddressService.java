package cln.swiggy.restaurant.service;

import cln.swiggy.restaurant.exception.ResourceNotFoundException;
import cln.swiggy.restaurant.model.request.AddressRequest;
import cln.swiggy.restaurant.model.response.CommonResponse;
import org.springframework.http.ResponseEntity;

public interface AddressService {
    ResponseEntity<CommonResponse> createAddress(Long restaurantId, AddressRequest request);
    ResponseEntity<CommonResponse> getAddress(Long restaurantId) throws ResourceNotFoundException;
    ResponseEntity<CommonResponse> updateAddress(Long restaurantId, AddressRequest request);
    ResponseEntity<CommonResponse> deleteAddress(Long restaurantId);
    ResponseEntity<CommonResponse> getRestaurantOutlets(Long restaurantId);
}