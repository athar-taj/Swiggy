package cln.swiggy.restaurant.migration.document;

import cln.swiggy.restaurant.model.enums.OfferType;
import jakarta.persistence.Column;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "offers")
public class OfferDocument {

    @Id
    private String id;

    private Long mysqlId;

    private String offerName;
    private String offerDescription;
    private Double offerDiscount;
    private OfferType offerType;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Boolean isActive = true;
    private Double minimumOrderValue;
    private Double maximumDiscountAmount;
    private String offerCode;
    private String termsAndConditions;
    private String applicableDays;
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @DBRef
    private RestaurantDocument restaurant;
}

