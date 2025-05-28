package cln.swiggy.restaurant.serviceImpl;

import cln.swiggy.restaurant.model.Facility;
import cln.swiggy.restaurant.model.Restaurant;
import cln.swiggy.restaurant.model.request.FacilityRequest;
import cln.swiggy.restaurant.model.response.CommonResponse;
import cln.swiggy.restaurant.repository.FacilityRepository;
import cln.swiggy.restaurant.service.FacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cln.swiggy.restaurant.exception.ResourceNotFoundException;
import cln.swiggy.restaurant.repository.RestaurantRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Service
public class FacilityServiceImpl implements FacilityService {

    @Autowired FacilityRepository facilityRepository;
    @Autowired RestaurantRepository restaurantRepository;

    @Override
    public ResponseEntity<CommonResponse> getFacilitiesByRestaurantId(Long restaurantId) {
        if (!restaurantRepository.existsById(restaurantId)) {
            throw new ResourceNotFoundException("Restaurant not found with id: " + restaurantId);
        }

        List<Facility> facilities = facilityRepository.findByRestaurantId(restaurantId);

        return ResponseEntity.ok(new CommonResponse(
                HttpStatus.OK.value(), "Facilities retrieved successfully", facilities));
    }
}
