package cln.swiggy.order.model.response;

import cln.swiggy.order.model.Order;
import cln.swiggy.order.model.enums.OrderStatus;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class OrderResponse {
    private Long userId;
    private Long menuId;
    private Long restaurantId;
    private OrderStatus orderStatus;
    private String deliveryAddress;
    private String receiverName;
    private String receiverPhoneNumber;
    private double price;
    private int quantity;
    private double total_price;
    private String createdAt;


    public OrderResponse convertToOrderResponse(Order order) {
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setUserId(order.getUserId());
        orderResponse.setMenuId(order.getMenuId());
        orderResponse.setOrderStatus(order.getOrderStatus());
        orderResponse.setDeliveryAddress(order.getDeliveryAddress());
        orderResponse.setReceiverName(order.getReceiverName());
        orderResponse.setReceiverPhoneNumber(order.getReceiverPhoneNumber());
        orderResponse.setPrice(order.getPrice());
        orderResponse.setQuantity(order.getQuantity());
        orderResponse.setTotal_price(order.getTotal_price());
        orderResponse.setCreatedAt(order.getCreatedAt().toString());
        return orderResponse;
    }
}
