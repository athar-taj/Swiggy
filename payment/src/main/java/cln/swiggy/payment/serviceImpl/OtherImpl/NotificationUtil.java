package cln.swiggy.payment.serviceImpl.OtherImpl;

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
}
