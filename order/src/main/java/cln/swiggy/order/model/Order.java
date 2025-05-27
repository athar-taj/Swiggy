package cln.swiggy.order.model;

import cln.swiggy.order.model.enums.DeliveryStatus;
import cln.swiggy.order.model.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "orders")  // Changed from 'order' to 'orders'
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long userId;
    @Column(nullable = false)
    private Long menuId;
    @Column(nullable = false)
    private Long restaurantId;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus = OrderStatus.PENDING;
    @Column(nullable = false)
    private String deliveryAddress;
    private String deliveryCity;
    @Column(nullable = false)
    private String pincode;
    private String deliveryLandmark;
    @Column(nullable = false)
    private String receiverName;
    @Column(nullable = false)
    private String receiverPhoneNumber;
    private double price;
    private int quantity;
    private double total_price;
    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}