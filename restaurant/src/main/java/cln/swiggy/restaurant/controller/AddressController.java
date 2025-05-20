package cln.swiggy.restaurant.controller;

import cln.swiggy.restaurant.model.request.AddressRequest;
import cln.swiggy.restaurant.model.response.CommonResponse;
import cln.swiggy.restaurant.service.AddressService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/restaurants")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping("/{restaurantId}/address")
    public ResponseEntity<CommonResponse> createAddress(
            @PathVariable Long restaurantId,
            @Valid @RequestBody AddressRequest request) {
        return addressService.createAddress(restaurantId, request);
    }

    @GetMapping("/{restaurantId}/address")
    public ResponseEntity<CommonResponse> getAddress(@PathVariable Long restaurantId) {
        return addressService.getAddress(restaurantId);
    }

    @PutMapping("/{restaurantId}/address")
    public ResponseEntity<CommonResponse> updateAddress(
            @PathVariable Long restaurantId,
            @Valid @RequestBody AddressRequest request) {
        return addressService.updateAddress(restaurantId, request);
    }

    @DeleteMapping("/{restaurantId}/address")
    public ResponseEntity<CommonResponse> deleteAddress(@PathVariable Long restaurantId) {
        return addressService.deleteAddress(restaurantId);
    }
}