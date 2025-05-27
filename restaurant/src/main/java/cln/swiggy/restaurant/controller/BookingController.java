package cln.swiggy.restaurant.controller;

import cln.swiggy.restaurant.model.request.BookingRequest;
import cln.swiggy.restaurant.model.response.CommonResponse;
import cln.swiggy.restaurant.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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

    @Operation(summary = "Create a new booking", description = "Creates a new restaurant table booking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking created successfully",
                    content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<CommonResponse> createBooking(
            @Valid @RequestBody BookingRequest request) {
        return bookingService.bookTable(request);
    }

    @Operation(summary = "Get booking details", description = "Retrieves details of a specific booking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking details retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Booking not found")
    })
    @GetMapping("/{bookingId}")
    public ResponseEntity<CommonResponse> getBookingDetails(
            @Parameter(description = "ID of the booking to retrieve")
            @PathVariable @NotNull Long bookingId) {
        return bookingService.getBookingDetails(bookingId);
    }

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

    @Operation(summary = "Get user bookings", description = "Retrieves all bookings for a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User bookings retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<CommonResponse> getUserBookings(
            @Parameter(description = "ID of the user")
            @PathVariable @NotNull Long userId) {
        return bookingService.getAllBookingsForUser(userId);
    }

    @Operation(summary = "Update booking", description = "Updates an existing booking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking updated successfully"),
            @ApiResponse(responseCode = "404", description = "Booking not found"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @PutMapping("/{bookingId}")
    public ResponseEntity<CommonResponse> updateBooking(
            @Parameter(description = "ID of the booking to update")
            @PathVariable @NotNull Long bookingId,
            @Valid @RequestBody BookingRequest request) {
        return bookingService.updateBooking(bookingId, request);
    }

    @Operation(summary = "Cancel booking", description = "Cancels an existing booking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking cancelled successfully"),
            @ApiResponse(responseCode = "404", description = "Booking not found"),
            @ApiResponse(responseCode = "400", description = "Invalid booking state")
    })
    @DeleteMapping("/{bookingId}")
    public ResponseEntity<CommonResponse> cancelBooking(
            @Parameter(description = "ID of the booking to cancel")
            @PathVariable @NotNull Long bookingId) {
        return bookingService.cancelBooking(bookingId);
    }
}