package cln.swiggy.restaurant.model;

import cln.swiggy.restaurant.model.enums.OfferType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;


import java.time.LocalDateTime;

@Data
@Entity
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @Column(nullable = false)
    private String offerName;

    @Column(nullable = false)
    private String offerDescription;

    @Column(nullable = false)
    private Double offerDiscount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OfferType offerType;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    @Column(nullable = false)
    private Boolean isActive = true;

    @Column(name = "min_order_value")
    private Double minimumOrderValue;

    @Column(name = "max_discount_amount")
    private Double maximumDiscountAmount;

    @Column(name = "offer_code", unique = true)
    private String offerCode;

    private String termsAndConditions;

    private String applicableDays;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}