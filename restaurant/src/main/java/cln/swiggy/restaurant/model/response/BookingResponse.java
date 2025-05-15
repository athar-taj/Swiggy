package cln.swiggy.restaurant.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponse {

    private String userId;
    private String restaurantId;
    private String bookingId;
    private String bookingStatus;
    private String bookingTime;
    private String bookingDate;

    public static BookingResponse convertToResponse(String userId, String restaurantId, String bookingId, String bookingStatus, String bookingTime, String bookingDate) {
        BookingResponse response = new BookingResponse();
        response.setUserId(userId);
        response.setRestaurantId(restaurantId);
        response.setBookingId(bookingId);
        response.setBookingStatus(bookingStatus);
        response.setBookingTime(bookingTime);
        response.setBookingDate(bookingDate);
        return response;
    }
}
