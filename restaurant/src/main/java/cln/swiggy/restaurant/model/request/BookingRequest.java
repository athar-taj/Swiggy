package cln.swiggy.restaurant.model.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

@Data
public class BookingRequest {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Restaurant ID is required")
    private Long restaurantId;

    @Min(value = 1, message = "Number of people must be at least 1")
    private int numberOfPeople;

    @NotNull(message = "Booking date is required")
    @Future(message = "Booking date must be in the future")
    private LocalDate bookingDate;

    @NotNull(message = "Booking time is required")
    private LocalTime bookingTime;

    private Long offerId;

    public LocalDateTime getBookingDateTime() {
        return bookingDate != null && bookingTime != null
                ? LocalDateTime.of(bookingDate, bookingTime) : null;
    }

    public void validateBookingDateTime() {
        LocalDateTime bookingDateTime = getBookingDateTime();
        if (bookingDateTime != null && !bookingDateTime.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("Booking date and time must be in the future");
        }
    }
}
