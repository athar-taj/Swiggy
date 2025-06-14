package cln.swiggy.order.serviceImpl;

import cln.swiggy.order.exception.ResourceNotFoundException;
import cln.swiggy.order.model.Order;
import cln.swiggy.order.model.OrdersCategory;
import cln.swiggy.order.model.TrackingCheckpoint;
import cln.swiggy.order.model.enums.DeliveryStatus;
import cln.swiggy.order.model.enums.OrderStatus;
import cln.swiggy.order.model.request.OrderRequest;
import cln.swiggy.order.model.response.CommonResponse;
import cln.swiggy.order.model.response.OrderResponse;
import cln.swiggy.order.model.response.OrderTrackingResponse;
import cln.swiggy.order.repository.OrderRepository;
import cln.swiggy.order.repository.OrdersCategoryRepository;
import cln.swiggy.order.service.OrderService;
import cln.swiggy.order.serviceImpl.otherImpl.EntityValidator;
import cln.swiggy.order.serviceImpl.otherImpl.NotificationUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;


@Service
public class OrderServiceImpl implements OrderService {

    @Value("${rabbitmq.notification.restaurant.exchange}")
    private String notificationExchange;

    @Value("${rabbitmq.notification.restaurant.routing_Key}")
    private String notificationRoutingKey;

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrdersCategoryRepository ordersCategoryRepository;
    @Autowired
    OrderResponse orderResponse;
    @Autowired
    EntityValidator entityValidator;
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Override
    public ResponseEntity<CommonResponse> createOrder(OrderRequest request) {
        entityValidator.validateUserExists(request.getUserId());
        entityValidator.validateRestaurantExists(request.getRestaurantId());

        HashMap<String,Object> data = (HashMap<String, Object>) rabbitTemplate.convertSendAndReceive("menu_exchange","price_order_key",request.getMenuId());
        if (data == null) {
            return ResponseEntity.badRequest().body(new CommonResponse(HttpStatus.BAD_REQUEST.value(),
                    "Menu not found with id: " + request.getMenuId(), null));
        }

        Order order = new Order();
        order.setUserId(request.getUserId());
        order.setMenuId(request.getMenuId());
        order.setRestaurantId(request.getRestaurantId());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setDeliveryAddress(request.getDeliveryAddress());
        order.setDeliveryCity(request.getDeliveryCity());
        order.setPincode(request.getDeliveryPincode());
        order.setDeliveryLandmark(request.getDeliveryLandmark());
        order.setReceiverName(request.getReceiverName());
        order.setReceiverPhoneNumber(request.getReceiverPhoneNumber());
        order.setPrice((Double) data.get("Price"));
        order.setQuantity(request.getQuantity());
        order.setTotal_price((Double) data.get("Price") * request.getQuantity());
        order.setDeliveryStatus(DeliveryStatus.PENDING);
        order.setCurrentLatitude(request.getCurrentLatitude());
        order.setCurrentLongitude(request.getCurrentLongitude());
        order.setEstimatedDeliveryTime(data.get("AvgTime") + " mins");
        order.setDeliveryPartnerName(request.getDeliveryPartnerName());
        order.setDeliveryPartnerPhone(request.getDeliveryPartnerPhone());
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());

        Order savedOrder = orderRepository.save(order);

        if(ordersCategoryRepository.existsByName((String) data.get("Category"))){
            OrdersCategory orders =  ordersCategoryRepository.findByName((String) data.get("Category"));
            orders.setTotalOrders(orders.getTotalOrders() + 1);
        }
        else {
            OrdersCategory orders = new OrdersCategory();
            orders.setName((String) data.get("Category"));
            orders.setTotalOrders(1);
            ordersCategoryRepository.save(orders);
        }
        Boolean result = (Boolean) rabbitTemplate.convertSendAndReceive("order_exchange", "update_order_key", savedOrder.getMenuId().toString());

        HashMap<String, Object> notificationData = NotificationUtil.getNotificationData(request.getRestaurantId(), "RESTAURANT","ORDER_PLACE", LocalDateTime.now());
        rabbitTemplate.convertAndSend(notificationExchange, notificationRoutingKey, notificationData);

        return ResponseEntity.ok( new CommonResponse(HttpStatus.OK.value(), "Order created successfully", savedOrder));
    }

    @Override
    public ResponseEntity<CommonResponse> getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));

        return ResponseEntity.ok(new CommonResponse(HttpStatus.OK.value(),"Order retrieved successfully", orderResponse.convertToOrderResponse(order)));
    }

    @Override
    public ResponseEntity<CommonResponse> updateOrder(Long orderId, OrderRequest request) {
        Order existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));

        entityValidator.validateUserExists(request.getUserId());
        entityValidator.validateMenuExists(request.getMenuId());
        entityValidator.validateRestaurantExists(request.getRestaurantId());

        existingOrder.setUserId(request.getUserId());
        existingOrder.setMenuId(request.getMenuId());
        existingOrder.setRestaurantId(request.getRestaurantId());
        existingOrder.setDeliveryAddress(request.getDeliveryAddress());
        existingOrder.setDeliveryCity(request.getDeliveryCity());
        existingOrder.setPincode(request.getDeliveryPincode());
        existingOrder.setDeliveryLandmark(request.getDeliveryLandmark());
        existingOrder.setReceiverName(request.getReceiverName());
        existingOrder.setReceiverPhoneNumber(request.getReceiverPhoneNumber());
        existingOrder.setQuantity(request.getQuantity());
        existingOrder.setUpdatedAt(LocalDateTime.now());

        Order updatedOrder = orderRepository.save(existingOrder);

        HashMap<String, Object> notificationData = NotificationUtil.getNotificationData(request.getRestaurantId(), "RESTAURANT","ORDER_UPDATE", LocalDateTime.now());
        rabbitTemplate.convertAndSend(notificationExchange, notificationRoutingKey, notificationData);

        return ResponseEntity.ok(new CommonResponse(HttpStatus.OK.value(),"Order updated successfully",
                orderResponse.convertToOrderResponse(updatedOrder)));
    }

    @Override
    public ResponseEntity<CommonResponse> cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));

        if (order.getOrderStatus() == OrderStatus.DELIVERED) {
            return ResponseEntity.badRequest().body(new CommonResponse(HttpStatus.BAD_REQUEST.value(),
                            "Cannot cancel order that has been delivered",null));
        }

        if (order.getOrderStatus() == OrderStatus.CANCELLED) {
            return ResponseEntity.badRequest()
                    .body(new CommonResponse(HttpStatus.BAD_REQUEST.value(), "Order is already cancelled",
                            null));
        }

        order.setOrderStatus(OrderStatus.CANCELLED);
        order.setUpdatedAt(LocalDateTime.now());

        if (order.getDeliveryStatus() == DeliveryStatus.PENDING) {
            order.setDeliveryStatus(DeliveryStatus.CANCELLED);
        }

        Order cancelledOrder = orderRepository.save(order);

        HashMap<String, Object> notificationData = NotificationUtil.getNotificationData(order.getRestaurantId(), "RESTAURANT","CANCEL_ORDER", LocalDateTime.now());
        rabbitTemplate.convertAndSend(notificationExchange, notificationRoutingKey, notificationData);

        return ResponseEntity.ok(new CommonResponse(
                HttpStatus.OK.value(),"Order cancelled successfully",cancelledOrder));
    }

    @Override
    public ResponseEntity<CommonResponse> getUsersOrders(Long userId) {
        entityValidator.validateUserExists(userId);

        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        List<Order> userOrders = orderRepository.findByUserId(userId, sort);

        if (userOrders.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CommonResponse(
                    HttpStatus.NOT_FOUND.value(),"No orders found for user", null));
        }
        return ResponseEntity.ok(new CommonResponse(
                HttpStatus.OK.value(),"Orders retrieved successfully", userOrders));
    }

    @Override
    public ResponseEntity<CommonResponse> getRestaurantOrders(Long restaurantId) {
        entityValidator.validateRestaurantExists(restaurantId);
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        List<Order> restaurantOrders = orderRepository.findByRestaurantId(restaurantId, sort);

        if (restaurantOrders.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CommonResponse(
                    HttpStatus.NOT_FOUND.value(),"No orders found for restaurant", null));
        }

        List<OrderResponse> responses = restaurantOrders.stream().map(orderResponse::convertToOrderResponse).toList();
        return ResponseEntity.ok(new CommonResponse(
                HttpStatus.OK.value(),"Orders retrieved successfully", responses));
    }

    @Override
    public ResponseEntity<CommonResponse> getRestaurantOrdersByStatus(Long restaurantId, OrderStatus status) {

        entityValidator.validateRestaurantExists(restaurantId);

        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        List<Order> restaurantOrders;

        if (status != null) {
            restaurantOrders = orderRepository.findByRestaurantIdAndOrderStatus(restaurantId, status, sort);
        } else {
            restaurantOrders = orderRepository.findByRestaurantId(restaurantId, sort);
        }

        String message;
        int httpStatus;
        if (restaurantOrders.isEmpty()) {
            httpStatus = HttpStatus.NOT_FOUND.value();
            message = status != null ?
                    "No orders found for restaurant with status: " + status :
                    "No orders found for restaurant";
        } else {
            httpStatus = HttpStatus.OK.value();
            message = "Orders retrieved successfully";
        }

        return httpStatus == HttpStatus.OK.value() ? ResponseEntity.ok(new CommonResponse(
                httpStatus,message, restaurantOrders)) : ResponseEntity.status(httpStatus).body(new CommonResponse(
                httpStatus,message, null));

    }

    @Override
    public ResponseEntity<CommonResponse> trackOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));

        if (order.getDeliveryStatus() == DeliveryStatus.CANCELLED) {
            return ResponseEntity.badRequest().body(new CommonResponse(HttpStatus.BAD_REQUEST.value(),
                    "Order has been cancelled", null));
        }

        OrderTrackingResponse trackingResponse = new OrderTrackingResponse();
        trackingResponse.setOrderId(order.getId());
        trackingResponse.setOrderStatus(order.getOrderStatus());
        trackingResponse.setDeliveryStatus(order.getDeliveryStatus());
        trackingResponse.setCurrentLatitude(order.getCurrentLatitude());
        trackingResponse.setCurrentLongitude(order.getCurrentLongitude());
        trackingResponse.setEstimatedDeliveryTime(order.getEstimatedDeliveryTime());
        trackingResponse.setDeliveryPartnerName(order.getDeliveryPartnerName());
        trackingResponse.setDeliveryPartnerPhone(order.getDeliveryPartnerPhone());
        trackingResponse.setLastUpdated(order.getUpdatedAt());

        trackingResponse.setCheckpoints(List.of(
                new TrackingCheckpoint("Order Placed", order.getCreatedAt(), "Your order has been placed"),
                new TrackingCheckpoint("Preparing", order.getCreatedAt().plusMinutes(5), "Restaurant started preparing"),
                new TrackingCheckpoint("Out for Delivery", order.getCreatedAt().plusMinutes(20), "Delivery partner picked up")
        ));

        return ResponseEntity.ok(new CommonResponse(HttpStatus.OK.value(), "Order tracking info", trackingResponse));
    }

    @Override
    public ResponseEntity<CommonResponse> getOrderHistory(Long userId) {
        entityValidator.validateUserExists(userId);
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        List<Order> userOrders = orderRepository.findByUserId(userId, sort);
        if (userOrders.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CommonResponse(
                    HttpStatus.NOT_FOUND.value(),"No orders found for user", null));
        }

        return ResponseEntity.ok(new CommonResponse(200, "Order history retrieved successfully", userOrders.stream().map(orderResponse::convertToOrderResponse).toList()));
    }

    public ResponseEntity<CommonResponse> updateOrderLocation(Long orderId, Double latitude, Double longitude) {
        Order existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
        existingOrder.setCurrentLatitude(latitude);
        existingOrder.setCurrentLongitude(longitude);
        Order updatedOrder = orderRepository.save(existingOrder);
        return ResponseEntity.ok(new CommonResponse(HttpStatus.OK.value(),"Order location updated successfully",
                orderResponse.convertToOrderResponse(updatedOrder)));
    }

}
