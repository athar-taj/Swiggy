package cln.swiggy.user.service;

import cln.swiggy.user.model.request.AddressRequest;
import cln.swiggy.user.model.response.CommonResponse;
import org.springframework.http.ResponseEntity;

public interface AddressService {

    ResponseEntity<CommonResponse> addAddress(AddressRequest addressRequest);
    ResponseEntity<CommonResponse> updateAddress(Long addressId, AddressRequest addressRequest);
    ResponseEntity<CommonResponse> deleteAddress(Long addressId);
    ResponseEntity<CommonResponse> getAddressById(Long addressId);
    ResponseEntity<CommonResponse> getAllUsersAddress(Long userId);
}
