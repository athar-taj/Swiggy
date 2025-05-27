package cln.swiggy.partner.service;

import cln.swiggy.partner.model.request.FacilityRequest;
import cln.swiggy.partner.model.response.CommonResponse;
import org.springframework.http.ResponseEntity;

public interface FacilityService {
    ResponseEntity<CommonResponse> addFacility(FacilityRequest request);
    ResponseEntity<CommonResponse> updateFacility(Long id, FacilityRequest request);
    ResponseEntity<CommonResponse> deleteFacility(Long id);
    ResponseEntity<CommonResponse> getFacilitiesByRestaurantId(Long restaurantId);
}
