package cln.swiggy.user.service;

import cln.swiggy.user.model.request.UserRequest;
import cln.swiggy.user.model.response.CommonResponse;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<CommonResponse> registerUser(UserRequest userRequest);

    ResponseEntity<CommonResponse> loginUser(String phoneNo);

    ResponseEntity<CommonResponse> verifyOtp(String phoneNo,String otp);

    ResponseEntity<CommonResponse> getAllUsers();

    ResponseEntity<CommonResponse> getUserById(Long userId);

    ResponseEntity<CommonResponse> updateUser(Long userId, UserRequest userRequest);

    ResponseEntity<CommonResponse> deleteUser(Long userId);

}
