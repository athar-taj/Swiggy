package cln.swiggy.restaurant.controller;

import cln.swiggy.restaurant.model.request.FacilityRequest;
import cln.swiggy.restaurant.model.response.CommonResponse;
import cln.swiggy.restaurant.service.FacilityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/facilities")
public class FacilityController {

    @Autowired
    private FacilityService facilityService;

    @PostMapping
    public ResponseEntity<CommonResponse> addFacility(@Valid @RequestBody FacilityRequest request) {
        return facilityService.addFacility(request);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse> updateFacility(
            @PathVariable Long id,
            @Valid @RequestBody FacilityRequest request) {
        return facilityService.updateFacility(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse> deleteFacility(@PathVariable Long id) {
        return facilityService.deleteFacility(id);
    }

    @GetMapping("/restaurants/{restaurantId}")
    public ResponseEntity<CommonResponse> getFacilitiesByRestaurant(@PathVariable Long restaurantId) {
        return facilityService.getFacilitiesByRestaurantId(restaurantId);
    }
}