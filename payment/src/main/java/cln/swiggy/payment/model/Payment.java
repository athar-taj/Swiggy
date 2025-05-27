package cln.swiggy.payment.model;

import cln.swiggy.payment.model.enums.PaymentMethod;
import cln.swiggy.payment.model.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private Long userId;
    @Column(unique = true)
    private Long orderId;
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    @Column(unique = true)
    private double amount;
    private String transactionId;
    private LocalDateTime createdAt;

}
