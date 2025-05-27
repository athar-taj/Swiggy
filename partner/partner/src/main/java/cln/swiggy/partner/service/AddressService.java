package cln.swiggy.partner.service;

import cln.swiggy.partner.exception.ResourceNotFoundException;
import cln.swiggy.partner.model.request.AddressRequest;
import cln.swiggy.partner.model.response.CommonResponse;
import org.springframework.http.ResponseEntity;

public interface AddressService {
    ResponseEntity<CommonResponse> createAddress(AddressRequest request);
    ResponseEntity<CommonResponse> getAddress(Long restaurantId) throws ResourceNotFoundException;
    ResponseEntity<CommonResponse> updateAddress(Long restaurantId, AddressRequest request);
    ResponseEntity<CommonResponse> deleteAddress(Long restaurantId);
    ResponseEntity<CommonResponse> getRestaurantOutlets(Long restaurantId);
}