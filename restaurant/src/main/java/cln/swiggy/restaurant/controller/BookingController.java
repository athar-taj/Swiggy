package cln.swiggy.restaurant.controller;

import cln.swiggy.restaurant.model.request.BookingRequest;
import cln.swiggy.restaurant.model.response.CommonResponse;
import cln.swiggy.restaurant.service.BookingService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity<CommonResponse> createBooking(@Valid @RequestBody BookingRequest request) {
        return bookingService.bookTable(request);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<CommonResponse> getBookingDetails(
            @PathVariable @NotNull Long bookingId) {
        return bookingService.getBookingDetails(bookingId);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<CommonResponse> getRestaurantBookings(
            @PathVariable @NotNull Long restaurantId) {
        return bookingService.getAllBookingsForRestaurant(restaurantId);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<CommonResponse> getUserBookings(
            @PathVariable @NotNull Long userId) {
        return bookingService.getAllBookingsForUser(userId);
    }

    @PutMapping("/{bookingId}")
    public ResponseEntity<CommonResponse> updateBooking(
            @PathVariable @NotNull Long bookingId,
            @Valid @RequestBody BookingRequest request) {
        return bookingService.updateBooking(bookingId, request);
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<CommonResponse> cancelBooking(
            @PathVariable @NotNull Long bookingId) {
        return bookingService.cancelBooking(bookingId);
    }
}