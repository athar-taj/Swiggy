package cln.swiggy.notification.model.response;

import cln.swiggy.notification.model.Notification;
import cln.swiggy.notification.model.enums.NotificationType;
import cln.swiggy.notification.model.enums.ReceiverType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class NotificationResponse {
    private Long receiverId;
    private ReceiverType receiverType;
    private String type;
    private String title;
    private String message;
    private NotificationType notificationType;
    private Boolean isRead;
    private LocalDateTime createdAt;
    private LocalDateTime sentAt;


    public static NotificationResponse convertToResponse(Notification notification) {
        NotificationResponse notificationResponse = new NotificationResponse();
        notificationResponse.setReceiverId(notification.getReceiverId());
        notificationResponse.setReceiverType(ReceiverType.valueOf(notification.getReceiverType().toString()));
        notificationResponse.setType(notification.getType());
        notificationResponse.setTitle(notification.getTitle());
        notificationResponse.setMessage(notification.getMessage());
        notificationResponse.setNotificationType(NotificationType.valueOf(notification.getNotificationType().toString()));
        notificationResponse.setIsRead(notification.getIsRead());
        notificationResponse.setCreatedAt(notification.getCreatedAt());
        notificationResponse.setSentAt(notification.getSentAt());

        return notificationResponse;
    }

    public static List<NotificationResponse> convertToResponse(List<Notification> notifications){
        List<NotificationResponse> notificationResponses = new ArrayList<>();
        for (Notification n : notifications){
            notificationResponses.add(convertToResponse(n));
        }
        return notificationResponses;
    }

}
