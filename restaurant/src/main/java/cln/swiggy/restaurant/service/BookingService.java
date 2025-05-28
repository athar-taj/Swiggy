package cln.swiggy.restaurant.service;

import cln.swiggy.restaurant.model.request.BookingRequest;
import cln.swiggy.restaurant.model.response.CommonResponse;
import org.springframework.http.ResponseEntity;

public interface BookingService {
    public ResponseEntity<CommonResponse> bookTable(BookingRequest request);
    public ResponseEntity<CommonResponse> getBookingDetails(Long bookingId);
    public ResponseEntity<CommonResponse> getAllBookingsForUser(Long userId);
    public ResponseEntity<CommonResponse> updateBooking(Long bookingId,BookingRequest request);
    public ResponseEntity<CommonResponse> cancelBooking(Long bookingId,String reason);
}
