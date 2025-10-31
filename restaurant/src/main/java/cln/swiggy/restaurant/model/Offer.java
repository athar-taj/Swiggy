package cln.swiggy.restaurant.model;

import cln.swiggy.restaurant.model.enums.OfferType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "offer")
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @Column(nullable = false,name = "offer_name")
    private String offerName;

    @Column(nullable = false,name = "offer_description")
    private String offerDescription;

    @Column(nullable = false, name = "offer_discount")
    private Double offerDiscount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,name = "offer_type")
    private OfferType offerType;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    @Column(nullable = false ,name = "is_active")
    private Boolean isActive = true;

    @Column(name = "min_order_value")
    private Double minimumOrderValue;

    @Column(name = "max_discount_amount")
    private Double maximumDiscountAmount;

    @Column(name = "offer_code", unique = true)
    private String offerCode;

    @Column(name = "terms_and_conditions")
    private String termsAndConditions;

    @Column(name = "applicable_days")
    private String applicableDays;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
