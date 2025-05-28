package cln.swiggy.restaurant.serviceImpl;

import cln.swiggy.restaurant.exception.ResourceNotFoundException;
import cln.swiggy.restaurant.model.Address;
import cln.swiggy.restaurant.model.Restaurant;
import cln.swiggy.restaurant.model.response.AddressResponse;
import cln.swiggy.restaurant.repository.AddressRepository;
import cln.swiggy.restaurant.repository.RestaurantRepository;
import cln.swiggy.restaurant.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cln.swiggy.restaurant.model.response.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    AddressRepository addressRepository;
    @Autowired
    RestaurantRepository restaurantRepository;

    @Override
    public ResponseEntity<CommonResponse> getRestaurantOutlets(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + restaurantId));

        List<Address> outlets = addressRepository.findAllByRestaurant(restaurant);

        return ResponseEntity.ok(new CommonResponse(
                HttpStatus.OK.value(),
                "Restaurant outlets retrieved successfully",
                outlets.stream().map(AddressResponse::convertToResponse).collect(Collectors.toList())
        ));
    }
}