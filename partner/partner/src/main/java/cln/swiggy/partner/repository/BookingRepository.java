package cln.swiggy.partner.repository;

import cln.swiggy.partner.model.Booking;
import cln.swiggy.partner.model.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByRestaurantIdAndBookingStatus(Long restaurantId, BookingStatus bookingStatus);

    List<Booking> findByUserId(Long userId);
}
