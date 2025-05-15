package cln.swiggy.restaurant.service;

import cln.swiggy.restaurant.model.request.FacilityRequest;
import cln.swiggy.restaurant.model.response.CommonResponse;
import org.springframework.http.ResponseEntity;

public interface FacilityService {
    ResponseEntity<CommonResponse> addFacility(FacilityRequest request);
    ResponseEntity<CommonResponse> updateFacility(Long id, FacilityRequest request);
    ResponseEntity<CommonResponse> deleteFacility(Long id);
    ResponseEntity<CommonResponse> getFacilitiesByRestaurantId(Long restaurantId);
}
