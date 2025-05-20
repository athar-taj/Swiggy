package cln.swiggy.order.service;

import cln.swiggy.order.model.enums.OrderStatus;
import cln.swiggy.order.model.request.OrderRequest;
import cln.swiggy.order.model.response.CommonResponse;
import org.springframework.http.ResponseEntity;

public interface OrderService {

    ResponseEntity<CommonResponse> createOrder(OrderRequest request);
    ResponseEntity<CommonResponse> getOrderById(Long orderId);
    ResponseEntity<CommonResponse> updateOrder(Long orderId, OrderRequest request);
    ResponseEntity<CommonResponse> cancelOrder(Long orderId);
    ResponseEntity<CommonResponse> getUsersOrders(Long userId);
    ResponseEntity<CommonResponse> getRestaurantOrders(Long restaurantId);
    ResponseEntity<CommonResponse> getRestaurantOrdersByStatus(Long restaurantId, OrderStatus status);
}
