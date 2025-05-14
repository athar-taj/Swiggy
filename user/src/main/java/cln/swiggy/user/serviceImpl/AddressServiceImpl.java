package cln.swiggy.user.serviceImpl;

import cln.swiggy.user.model.Address;
import cln.swiggy.user.model.User;
import cln.swiggy.user.model.request.AddressRequest;
import cln.swiggy.user.model.response.AddressResponse;
import cln.swiggy.user.model.response.CommonResponse;
import cln.swiggy.user.repository.AddressRepository;
import cln.swiggy.user.repository.UserRepository;
import cln.swiggy.user.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired UserRepository userRepository;
    @Autowired AddressRepository addressRepository;

    @Override
    public ResponseEntity<CommonResponse> addAddress(AddressRequest addressRequest) {
        Optional<User> user = userRepository.findById(addressRequest.getUserId());

        if (user.isEmpty()) {
            return ResponseEntity.status(404).body(new CommonResponse(404,"User not found with id: " + addressRequest.getUserId(),null));
        }

        if (user.get().getAddresses().stream().anyMatch(address -> address.getAddressType() == addressRequest.getAddressType())) {
            return ResponseEntity.status(409).body(new CommonResponse(409,"Address already exists for user with id: " + addressRequest.getUserId(),null));
        }

        Address address = new Address();
        address.setUser(user.get());
        address.setAddressType(addressRequest.getAddressType());
        address.setAddress(addressRequest.getAddress());
        address.setCity(addressRequest.getCity());
        address.setState(addressRequest.getState());
        address.setPincode(addressRequest.getPincode());
        address.setCreatedAt(LocalDateTime.now());

        addressRepository.save(address);
        return ResponseEntity.ok(new CommonResponse(200,addressRequest.getAddressType() + " Address added successfully",null));
    }

    @Override
    public ResponseEntity<CommonResponse> updateAddress(Long addressId, AddressRequest addressRequest) {
        Optional<Address> address = addressRepository.findById(addressId);

        if (address.isEmpty()) {
            return ResponseEntity.status(404).body(new CommonResponse(404,"Address not found with id: " + addressId,null));
        }

        if (address.get().getUser().getId() != addressRequest.getUserId()) {
            return ResponseEntity.status(400).body(new CommonResponse(400,"Address not found for user with id: " + addressRequest.getUserId(),null));
        }

        address.get().setAddress(addressRequest.getAddress());
        address.get().setCity(addressRequest.getCity());
        address.get().setState(addressRequest.getState());
        address.get().setPincode(addressRequest.getPincode());
        address.get().setUpdatedAt(LocalDateTime.now());

        addressRepository.save(address.get());
        return ResponseEntity.ok(new CommonResponse(200,addressRequest.getAddressType() + " Address updated successfully",null));
    }

    @Override
    public ResponseEntity<CommonResponse> deleteAddress(Long addressId) {
        Optional<Address> address = addressRepository.findById(addressId);

        if (address.isEmpty()) {
            return ResponseEntity.status(404).body(new CommonResponse(404,"Address not found with id: " + addressId,null));
        }

        addressRepository.delete(address.get());
        return ResponseEntity.ok(new CommonResponse(200,address.get().getAddressType() + " Address deleted successfully",null));
    }

    @Override
    public ResponseEntity<CommonResponse> getAddressById(Long addressId) {
        Optional<Address> address = addressRepository.findById(addressId);

        if (address.isEmpty()) {
            return ResponseEntity.status(404).body(new CommonResponse(404,"Address not found with id: " + addressId,null));
        }

        return ResponseEntity.ok(new CommonResponse(200,"Address fetched successfully",address.get()));
    }

    @Override
    public ResponseEntity<CommonResponse> getAllUsersAddress(Long userId) {
        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            return ResponseEntity.status(404).body(new CommonResponse(404,"User not found with id: " + userId,null));
        }

        List<AddressResponse> addresses = convertToAddress(user.get().getAddresses());
        return ResponseEntity.ok(new CommonResponse(200,user.get().getName() +"'s Addresses fetched successfully", addresses));
    }

    private List<AddressResponse> convertToAddress(List<Address> addresses) {
        return addresses.stream().map(address -> {
            AddressResponse response = new AddressResponse();
            response.setAddressType(address.getAddressType());
            response.setAddress(address.getAddress());
            response.setCity(address.getCity());
            response.setState(address.getState());
            response.setPincode(address.getPincode());
            return response;
        }).toList();
    }
}
