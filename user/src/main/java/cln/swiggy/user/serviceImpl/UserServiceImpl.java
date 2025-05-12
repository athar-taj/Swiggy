package cln.swiggy.user.serviceImpl;

import cln.swiggy.user.model.User;
import cln.swiggy.user.model.enums.UserRole;
import cln.swiggy.user.model.request.UserRequest;
import cln.swiggy.user.model.response.CommonResponse;
import cln.swiggy.user.model.response.UserResponse;
import cln.swiggy.user.repository.UserRepository;
import cln.swiggy.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    private static final int OTP_EXPIRY_MINUTES = 10;

    @Override
    public ResponseEntity<CommonResponse> registerUser(UserRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new CommonResponse(409,"User already exists with email: " + request.getEmail(),null));
        }

        if (userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new CommonResponse(409,"User already exists with Phone No. : " + request.getPhoneNumber(),null));
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setLatitude(request.getLatitude());
        user.setLongitude(request.getLongitude());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setAddresses(new ArrayList<>());
        user.setIsAvailable(false);
        user.setOtp(generateOtp());
        user.setOtpExpiry(LocalDateTime.now().plusMinutes(OTP_EXPIRY_MINUTES));

        UserRole role = (request.getRole() != null) ? request.getRole() : UserRole.USER;
        user.setRole(role);

        userRepository.save(user);

        return ResponseEntity.ok(new CommonResponse(200,"User registered successfully",convertToUserResponse(user)));
    }

    @Override
    public ResponseEntity<CommonResponse> loginUser(String phoneNo) {
        Optional<User> user = userRepository.findByPhoneNumber(phoneNo);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CommonResponse(404,"Not Found Phone No. : " + phoneNo,null));
        }

        // sending an otp email via SNS

        return ResponseEntity.ok(new CommonResponse(200,"OTP sent successfully",true));
    }

    @Override
    public ResponseEntity<CommonResponse> verifyOtp(String phoneNo,String otp) {
        Optional<User> user = userRepository.findByPhoneNumber(phoneNo);
        if (user.isEmpty()) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CommonResponse(404,"Not Found Phone No. : " + phoneNo,null));
        }
        if (user.get().getOtp() != Integer.parseInt(otp)) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CommonResponse(400,"Invalid OTP",null));
        }
        user.get().setIsAvailable(true);
        user.get().setOtp(0);
        user.get().setOtpExpiry(null);
        userRepository.save(user.get());
        return ResponseEntity.ok(new CommonResponse(200,"OTP Verified Successfully",null));
    }

    @Override
    public ResponseEntity<CommonResponse> getAllUsers() {
        List<UserResponse> userResponses = userRepository.findAll().stream().map(this::convertToUserResponse).toList();
        return ResponseEntity.ok(new CommonResponse(200,"Users fetched successfully",userResponses));
    }

    @Override
    public ResponseEntity<CommonResponse> getUserById(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CommonResponse(404,"User not found with id: " + userId,null));
        }
        return ResponseEntity.ok(new CommonResponse(200,"User fetched successfully",convertToUserResponse(user.get())));
    }

    @Override
    public ResponseEntity<CommonResponse> updateUser(Long userId, UserRequest userRequest) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CommonResponse(404,"User not found with id: " + userId,null));
        }
        user.get().setName(userRequest.getName());
        user.get().setLatitude(userRequest.getLatitude());
        user.get().setLongitude(userRequest.getLongitude());
        user.get().setUpdatedAt(LocalDateTime.now());
        userRepository.save(user.get());
        return ResponseEntity.ok(new CommonResponse(200,"User updated successfully",convertToUserResponse(user.get())));
    }

    @Override
    public ResponseEntity<CommonResponse> deleteUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CommonResponse(404,"User not found with id: " + userId,null));
        }
        userRepository.delete(user.get());
        return ResponseEntity.ok(new CommonResponse(200,"User deleted successfully",null));
    }

    private int generateOtp() {
        return (int) (Math.random() * 900000) + 100000;
    }

    private UserResponse convertToUserResponse(User user) {
        UserResponse response = new UserResponse();
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setPhoneNumber(user.getPhoneNumber());
        response.setRole(user.getRole());
        response.setLatitude(user.getLatitude());
        response.setLongitude(user.getLongitude());
        response.setIsAvailable(user.getIsAvailable());
        response.setIsAvailable(user.getIsAvailable());
        response.setCreatedAt(user.getCreatedAt());
        return response;
    }
}
