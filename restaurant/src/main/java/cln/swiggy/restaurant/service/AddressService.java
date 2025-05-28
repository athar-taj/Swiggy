package cln.swiggy.restaurant.service;

import cln.swiggy.restaurant.model.response.CommonResponse;
import org.springframework.http.ResponseEntity;

public interface AddressService {
    ResponseEntity<CommonResponse> getRestaurantOutlets(Long restaurantId);
}