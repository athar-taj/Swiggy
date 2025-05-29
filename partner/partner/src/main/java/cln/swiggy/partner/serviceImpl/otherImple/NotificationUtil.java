package cln.swiggy.partner.serviceImpl.otherImple;

import java.time.LocalDateTime;
import java.util.HashMap;

public class NotificationUtil {

    public static HashMap<String, Object> getNotificationData(Long restaurantId,String ReceiverType, String type, LocalDateTime time) {
        HashMap<String, Object> notificationData = new HashMap<>();
        notificationData.put("restaurantId", restaurantId);
        notificationData.put("receiverType", ReceiverType);
        notificationData.put("type", type);
        notificationData.put("time", time);
        return notificationData;
    }

    public static HashMap<String, Object> getNotificationDataUser(Long restaurantId
            ,Long userId,String ReceiverType, String type, LocalDateTime time) {
        HashMap<String, Object> notificationData = new HashMap<>();
        notificationData.put("restaurantId", restaurantId);
        notificationData.put("userId", userId);
        notificationData.put("ReceiverType", ReceiverType);
        notificationData.put("type", type);
        notificationData.put("time", time);
        return notificationData;
    }

}
