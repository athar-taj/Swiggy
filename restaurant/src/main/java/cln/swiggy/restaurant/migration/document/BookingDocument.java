package cln.swiggy.restaurant.migration.document;

import cln.swiggy.restaurant.model.enums.BookingStatus;
import jakarta.persistence.Column;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "bookings")
public class BookingDocument {

    @Id
    private String id;

    private Long mysqlId;

    private Long userId;
    private int numberOfPeople;
    private LocalDate bookingDate;
    private LocalTime bookingTime;
    private BookingStatus bookingStatus = BookingStatus.PENDING;
    private String cancellationReason;
    private Long offerId;
    @Column(name = "created_at")
    private LocalDateTime createdAt;


    @DBRef
    private RestaurantDocument restaurant;
}
