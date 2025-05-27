package cln.swiggy.partner.serviceImpl;

import cln.swiggy.partner.exception.ResourceNotFoundException;
import cln.swiggy.partner.model.Facility;
import cln.swiggy.partner.model.Restaurant;
import cln.swiggy.partner.model.request.FacilityRequest;
import cln.swiggy.partner.model.response.CommonResponse;
import cln.swiggy.partner.repository.FacilityRepository;
import cln.swiggy.partner.repository.RestaurantRepository;
import cln.swiggy.partner.service.FacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacilityServiceImpl implements FacilityService {

    @Autowired FacilityRepository facilityRepository;
    @Autowired RestaurantRepository restaurantRepository;

    @Override
    public ResponseEntity<CommonResponse> addFacility(FacilityRequest request) throws ResourceNotFoundException {
            Restaurant restaurant = restaurantRepository.findById(request.getRestaurantId())
                    .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + request.getRestaurantId()));

            Facility facility = new Facility();
            facility.setRestaurant(restaurant);
            facility.setFacilityName(request.getFacilityName());
            facility.setDescription(request.getDescription());

            Facility savedFacility = facilityRepository.save(facility);

            return ResponseEntity.ok(new CommonResponse(
                    HttpStatus.CREATED.value(), "Facility added successfully", savedFacility));
    }

    @Override
    public ResponseEntity<CommonResponse> updateFacility(Long id, FacilityRequest request) throws ResourceNotFoundException {
            Facility facility = facilityRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Facility not found with id: " + id));

            Restaurant restaurant = restaurantRepository.findById(request.getRestaurantId())
                    .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + request.getRestaurantId()));

            if (!facility.getRestaurant().getId().equals(restaurant.getId())) {
                throw new IllegalArgumentException("Facility does not belong to the "+ restaurant.getName());
            }

            facility.setFacilityName(request.getFacilityName());
            facility.setDescription(request.getDescription());

            Facility updatedFacility = facilityRepository.save(facility);

            return ResponseEntity.ok(new CommonResponse(
                    HttpStatus.OK.value(),"Facility updated successfully", updatedFacility));
    }

    @Override
    public ResponseEntity<CommonResponse> deleteFacility(Long id) throws ResourceNotFoundException {
        Facility facility = facilityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Facility not found with id: " + id));

        facilityRepository.delete(facility);

        return ResponseEntity.ok(new CommonResponse(
                HttpStatus.OK.value(), "Facility deleted successfully", null));
    }

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
