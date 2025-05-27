package cln.swiggy.partner.model;

import cln.swiggy.partner.model.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long userId;
    @ManyToOne
    @JoinColumn(name = "booking", nullable = false)
    private Restaurant restaurant;
    @Column(nullable = false)
    private int numberOfPeople;
    @Column(nullable = false)
    private LocalDate bookingDate;
    @Column(nullable = false)
    private LocalTime bookingTime;
    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus = BookingStatus.PENDING ;
    private Long OfferId;
    private LocalDateTime createdAt;
}
