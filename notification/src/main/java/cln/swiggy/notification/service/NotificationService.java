package cln.swiggy.notification.service;

import cln.swiggy.notification.model.response.CommonResponse;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

public interface NotificationService {

    ResponseEntity<CommonResponse> sendNotification(Long receiverId, String receiverType, String type, LocalDateTime createdAt, String message, String title, String notificationType);

    ResponseEntity<CommonResponse> markNotificationAsRead(Long notificationId);

    ResponseEntity<CommonResponse> getRestaurantsNotification(Long restaurantId);

    ResponseEntity<CommonResponse> getUsersNotification(Long userId);
}
