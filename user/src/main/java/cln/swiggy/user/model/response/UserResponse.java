package cln.swiggy.user.model.response;

import cln.swiggy.user.model.enums.UserRole;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponse {
    private String name;
    private String email;
    private String phoneNumber;
    private UserRole role;
    private Double latitude;
    private Boolean isAvailable;
    private Double longitude;
    private LocalDateTime createdAt;
}
