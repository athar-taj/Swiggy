package cln.swiggy.user.controller;

import cln.swiggy.user.config.JWTConfig;
import cln.swiggy.user.model.request.UserRequest;
import cln.swiggy.user.model.response.CommonResponse;
import cln.swiggy.user.repository.UserRepository;
import cln.swiggy.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private JWTConfig jwtConfig;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<CommonResponse> registerUser(@Valid @RequestBody UserRequest userRequest) {
        return userService.registerUser(userRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<CommonResponse> loginUser(@Valid @RequestParam("phoneNo") String phoneNo) {
        return userService.loginUser(phoneNo);
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<CommonResponse> verifyOtp(@RequestParam String phoneNo, @RequestParam String otp) {
        try {
            ResponseEntity<CommonResponse> isOtpValid = userService.verifyOtp(phoneNo, otp);
            if (isOtpValid != null && isOtpValid.getStatusCode().is2xxSuccessful()) {
                String token = jwtConfig.generateToken(phoneNo);
                return ResponseEntity.ok(new CommonResponse(200, "OTP verified successfully!", token));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CommonResponse(500, "OTP verification failed!", e.getMessage()));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new CommonResponse(400, "Invalid OTP!", null));
    }

    @GetMapping
    public ResponseEntity<CommonResponse> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<CommonResponse> getUserById(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<CommonResponse> updateUser(
            @PathVariable Long userId,
            @RequestBody UserRequest userRequest) {
        return userService.updateUser(userId, userRequest);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<CommonResponse> deleteUser(@PathVariable Long userId) {
        return userService.deleteUser(userId);
    }

}
