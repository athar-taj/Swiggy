package cln.swiggy.notification.controller;


import cln.swiggy.notification.model.response.CommonResponse;
import cln.swiggy.notification.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
@Tag(name = "Notifications", description = "APIs for handling notifications")
public class NotificationController {

    @Autowired
    NotificationService notificationService;

    @Operation(
            summary = "Mark notification as read",
            description = "Marks a specific notification as read using the notification ID",
            responses = {
                @ApiResponse(responseCode = "200", description = "Notification marked as read successfully"),
                @ApiResponse(responseCode = "404", description = "Notification not found"),
                @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @PutMapping("/mark-as-read/{notificationId}")
    public ResponseEntity<CommonResponse> markNotificationAsRead(
            @PathVariable Long notificationId
    ) {
        return notificationService.markNotificationAsRead(notificationId);
    }

    @Operation(
            summary = "Get restaurant notifications",
            description = "Retrieves all notifications for a specific restaurant using the restaurant ID",
            responses = {
                @ApiResponse(responseCode = "200", description = "Notifications retrieved successfully"),
                @ApiResponse(responseCode = "404", description = "Restaurant not found or no notifications"),
                @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<CommonResponse> getRestaurantsNotification(
            @PathVariable Long restaurantId
    ) {
        return notificationService.getRestaurantsNotification(restaurantId);
    }

    @Operation(
            summary = "Get user notifications",
            description = "Retrieves all notifications for a specific user using the user ID",
            responses = {
                @ApiResponse(responseCode = "200", description = "Notifications retrieved successfully"),
                @ApiResponse(responseCode = "404", description = "User not found or no notifications"),
                @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @GetMapping("/user/{userId}")
    public ResponseEntity<CommonResponse> getUsersNotification(
            @PathVariable Long userId
    ) {
        return notificationService.getUsersNotification(userId);
    }
}