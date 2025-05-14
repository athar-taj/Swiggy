package cln.swiggy.user.controller;

import cln.swiggy.user.model.request.AddressRequest;
import cln.swiggy.user.model.response.CommonResponse;
import cln.swiggy.user.service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/address")
@Tag(name = "Address", description = "User's Address related APIs")
@SecurityRequirement(name = "bearer-jwt")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @SecurityRequirement(name = "bearer-jwt")
    @Operation(summary = "Add User's Delivery Address")
    @PostMapping
    public ResponseEntity<CommonResponse> addAddress(@RequestBody AddressRequest addressRequest) {
        return addressService.addAddress(addressRequest);
    }

    @SecurityRequirement(name = "bearer-jwt")
    @Operation(summary = "Update user Address")
    @PutMapping("/{addressId}")
    public ResponseEntity<CommonResponse> updateAddress(
            @PathVariable Long addressId,
            @RequestBody AddressRequest addressRequest) {
        return addressService.updateAddress(addressId, addressRequest);
    }

    @SecurityRequirement(name = "bearer-jwt")
    @Operation(summary = "Delete Address")
    @DeleteMapping("/{addressId}")
    public ResponseEntity<CommonResponse> deleteAddress(@PathVariable Long addressId) {
        return addressService.deleteAddress(addressId);
    }

    @SecurityRequirement(name = "bearer-jwt")
    @Operation(summary = "Get Address By User ID")
    @GetMapping("/{addressId}")
    public ResponseEntity<CommonResponse> getAddressById(@PathVariable Long addressId) {
        return addressService.getAddressById(addressId);
    }

    @SecurityRequirement(name = "bearer-jwt")
    @Operation(summary = "User's All Delivery Addresses")
    @GetMapping("/user/{userId}")
    public ResponseEntity<CommonResponse> getAllUsersAddress(@PathVariable Long userId) {
        return addressService.getAllUsersAddress(userId);
    }
}
