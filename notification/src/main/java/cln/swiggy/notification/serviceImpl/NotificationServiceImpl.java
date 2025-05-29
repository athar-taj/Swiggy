package cln.swiggy.notification.serviceImpl;

import cln.swiggy.notification.model.Notification;
import cln.swiggy.notification.model.enums.NotificationType;
import cln.swiggy.notification.model.enums.ReceiverType;
import cln.swiggy.notification.model.response.CommonResponse;
import cln.swiggy.notification.repository.NotificationRepository;
import cln.swiggy.notification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    NotificationRepository notificationRepository;

    @Override
    public ResponseEntity<CommonResponse> sendNotification(Long receiverId, String receiverType, String type, LocalDateTime createdAt, String message, String title, String notificationType) {

        Notification notification = new Notification();
        notification.setReceiverId(receiverId);
        notification.setReceiverType(ReceiverType.valueOf(receiverType));
        notification.setType(type);
        notification.setMessage(message);
        notification.setTitle(title);
        notification.setNotificationType(NotificationType.valueOf(notificationType));
        notification.setCreatedAt(createdAt);
        notification.setSentAt(LocalDateTime.now());
        notificationRepository.save(notification);

        return ResponseEntity.ok(new CommonResponse(200,"Notification sent Successfully",true));
    }
    @Override
    public ResponseEntity<CommonResponse> markNotificationAsRead(Long notificationId) {
        Optional<Notification> notificationOptional = notificationRepository.findById(notificationId);

        if (notificationOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new CommonResponse(404,"Notification not found", false));
        }
        Notification notification = notificationOptional.get();
        notification.setIsRead(true);
        notificationRepository.save(notification);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponse(200,"Notification marked as read successfully", true));
    }

    public ResponseEntity<CommonResponse> getRestaurantsNotification(Long restaurantId){
        List<Notification> notifications = notificationRepository.findByReceiverIdAndReceiverType(restaurantId,ReceiverType.RESTAURANT.toString());
        if(notifications.isEmpty()){
            return ResponseEntity.ok(new CommonResponse(404,"No Notifications Found",null));
        }
        return ResponseEntity.ok(new CommonResponse(200,"Notifications fetched Successfully",notifications));
    }

    public ResponseEntity<CommonResponse> getUsersNotification(Long userId){
        List<Notification> notifications = notificationRepository.findByReceiverIdAndReceiverType(userId,ReceiverType.USER.toString());
        if(notifications.isEmpty()){
            return ResponseEntity.ok(new CommonResponse(404,"No Notifications Found",null));
        }
        return ResponseEntity.ok(new CommonResponse(200,"Notifications fetched Successfully",notifications));
    }


}