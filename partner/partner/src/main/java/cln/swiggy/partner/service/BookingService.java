package cln.swiggy.partner.service;


import cln.swiggy.partner.model.response.CommonResponse;
import org.springframework.http.ResponseEntity;

public interface BookingService {
    public ResponseEntity<CommonResponse> confirmBooking(Long bookingId);
    public ResponseEntity<CommonResponse> getAllBookingsForRestaurant(Long restaurantId);
}
