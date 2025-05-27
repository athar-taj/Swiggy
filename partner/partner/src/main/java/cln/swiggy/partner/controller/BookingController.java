package cln.swiggy.partner.controller;

import cln.swiggy.partner.model.response.CommonResponse;
import cln.swiggy.partner.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/restaurant/bookings")
@Tag(name = "Restaurant Bookings", description = "APIs for managing restaurant bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Operation(summary = "Get restaurant bookings", description = "Retrieves all bookings for a specific restaurant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurant bookings retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Restaurant not found")
    })
    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<CommonResponse> getRestaurantBookings(
            @Parameter(description = "ID of the restaurant")
            @PathVariable @NotNull Long restaurantId) {
        return bookingService.getAllBookingsForRestaurant(restaurantId);
    }

    @Operation(summary = "Confirm booking", description = "Confirms a pending booking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking confirmed successfully"),
            @ApiResponse(responseCode = "404", description = "Booking not found"),
            @ApiResponse(responseCode = "400", description = "Invalid booking state")
    })
    @PostMapping("/{bookingId}/confirm")
    public ResponseEntity<CommonResponse> confirmBooking(
            @Parameter(description = "ID of the booking to confirm")
            @PathVariable @NotNull Long bookingId) {
        return bookingService.confirmBooking(bookingId);
    }

}