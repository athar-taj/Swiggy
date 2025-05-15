package cln.swiggy.restaurant.serviceImpl;

import cln.swiggy.restaurant.exception.DuplicateResourceFoundException;
import cln.swiggy.restaurant.exception.ResourceNotFoundException;
import cln.swiggy.restaurant.model.Address;
import cln.swiggy.restaurant.model.Restaurant;
import cln.swiggy.restaurant.model.response.AddressResponse;
import cln.swiggy.restaurant.repository.AddressRepository;
import cln.swiggy.restaurant.repository.RestaurantRepository;
import cln.swiggy.restaurant.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cln.swiggy.restaurant.model.request.AddressRequest;
import cln.swiggy.restaurant.model.response.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;

import static cln.swiggy.restaurant.model.response.AddressResponse.convertToResponse;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    AddressRepository addressRepository;
    @Autowired
    RestaurantRepository restaurantRepository;

    @Override
    public ResponseEntity<CommonResponse> getAddress(Long restaurantId) throws ResourceNotFoundException {
        List<Address> address = addressRepository.findByRestaurantId(restaurantId);

        if (address.isEmpty()) {
            throw new ResourceNotFoundException("Address not found for restaurant id: " + restaurantId);
        }

        return ResponseEntity.ok(new CommonResponse(
                HttpStatus.OK.value(),"Address retrieved successfully", address));
    }

    @Override
    public ResponseEntity<CommonResponse> createAddress(Long restaurantId, AddressRequest request) throws ResourceNotFoundException{
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + restaurantId));

        if (addressRepository.existsByRestaurantId(restaurantId)) {
            throw new DuplicateResourceFoundException("Address already exists for restaurant id: " + restaurantId);
        }

        if(addressRepository.existsByOutlet(request.getOutlet())){
            throw new DuplicateResourceFoundException("Address already exists for outlet: " + request.getOutlet());
        }

        Address address = new Address();
        address.setRestaurantAddress(restaurant);
        address.setOutlet(request.getOutlet());
        address.setLatitude(request.getLatitude());
        address.setLongitude(request.getLongitude());
        address.setAddress(request.getAddress());
        address.setCity(request.getCity());
        address.setState(request.getState());
        address.setPincode(request.getPincode());

        Address savedAddress = addressRepository.save(address);

        return ResponseEntity.ok(new CommonResponse(HttpStatus.CREATED.value(),"Address created successfully",savedAddress));
    }

    @Override
    public ResponseEntity<CommonResponse> updateAddress(Long addressId, AddressRequest request) {
        Restaurant restaurant = restaurantRepository.findById(request.getRestaurantId())
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + request.getRestaurantId()));

        Address address = addressRepository.findById(addressId).orElseThrow(() -> new ResourceNotFoundException("Address not found with id: " + addressId));

        address.setOutlet(request.getOutlet());
        address.setLatitude(request.getLatitude());
        address.setLongitude(request.getLongitude());
        address.setAddress(request.getAddress());
        address.setCity(request.getCity());
        address.setState(request.getState());
        address.setPincode(request.getPincode());

        Address updatedAddress = addressRepository.save(address);

        return ResponseEntity.ok(new CommonResponse(
                HttpStatus.OK.value(),
                "Address updated successfully",
                convertToResponse(updatedAddress)
        ));
    }

    @Override
    public ResponseEntity<CommonResponse> deleteAddress(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + restaurantId));

        List<Address> address = addressRepository.findByRestaurantId(restaurantId);
        if (address.isEmpty()) {
            throw new ResourceNotFoundException("Address not found for restaurant id: " + restaurantId);
        }

        addressRepository.deleteAll(address);

        return ResponseEntity.ok(new CommonResponse(
                HttpStatus.OK.value(),
                "Address deleted successfully",
                null
        ));
    }

    @Override
    public ResponseEntity<CommonResponse> getRestaurantOutlets(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + restaurantId));

        List<Address> outlets = addressRepository.findAllByRestaurantId(restaurantId);

        return ResponseEntity.ok(new CommonResponse(
                HttpStatus.OK.value(),
                "Restaurant outlets retrieved successfully",
                outlets.stream().map(AddressResponse::convertToResponse).collect(Collectors.toList())
        ));
    }
}