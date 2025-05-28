package cln.swiggy.order.model.response;

import cln.swiggy.order.model.TrackingCheckpoint;
import cln.swiggy.order.model.enums.DeliveryStatus;
import cln.swiggy.order.model.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderTrackingResponse {
    private Long orderId;
    private DeliveryStatus deliveryStatus;
    private OrderStatus orderStatus;
    private String estimatedDeliveryTime;
    private double currentLatitude;
    private double currentLongitude;
    private String deliveryPartnerName;
    private String deliveryPartnerPhone;
    private LocalDateTime lastUpdated;
    private List<TrackingCheckpoint> checkpoints;
}
