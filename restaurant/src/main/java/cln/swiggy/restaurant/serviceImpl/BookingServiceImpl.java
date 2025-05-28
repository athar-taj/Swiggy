package cln.swiggy.restaurant.serviceImpl;

import cln.swiggy.restaurant.exception.ResourceNotFoundException;
import cln.swiggy.restaurant.model.Booking;
import cln.swiggy.restaurant.model.Restaurant;
import cln.swiggy.restaurant.model.enums.BookingStatus;
import cln.swiggy.restaurant.model.request.BookingRequest;
import cln.swiggy.restaurant.model.response.CommonResponse;
import cln.swiggy.restaurant.repository.BookingRepository;
import cln.swiggy.restaurant.repository.RestaurantRepository;
import cln.swiggy.restaurant.service.BookingService;
import com.aws.service.sns.service.SNSService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.routing-key}")
    private String routingKey;

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private SNSService snsService;

    @Override
    public ResponseEntity<CommonResponse> bookTable(BookingRequest request) {
        Booking booking = new Booking();
        Restaurant restaurant = restaurantRepository.findById(request.getRestaurantId())
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found"));

        Boolean isOwnerExists = (Boolean) rabbitTemplate.convertSendAndReceive(exchange, routingKey, request.getUserId());

        if (Boolean.FALSE.equals(isOwnerExists))
            throw new ResourceNotFoundException("Owner not found with id: " + request.getUserId());
        booking.setUserId(request.getUserId());

        booking.setRestaurant(restaurant);
        booking.setNumberOfPeople(request.getNumberOfPeople());
        booking.setBookingDate(request.getBookingDate());
        booking.setBookingTime(request.getBookingTime());
        booking.setBookingStatus(BookingStatus.PENDING);
        booking.setOfferId(request.getOfferId());
        booking.setCreatedAt(LocalDateTime.now());

        Booking savedBooking = bookingRepository.save(booking);

        return ResponseEntity.ok(new CommonResponse(
                HttpStatus.OK.value(), "Booking created successfully", savedBooking));
    }

    @Override
    public ResponseEntity<CommonResponse> getBookingDetails(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        return ResponseEntity.ok(new CommonResponse(HttpStatus.OK.value(),
                "Booking details retrieved successfully", booking));
    }


    @Override
    public ResponseEntity<CommonResponse> getAllBookingsForUser(Long userId) {
        List<Booking> bookings = bookingRepository.findByUserId(userId);

        return ResponseEntity.ok(new CommonResponse(HttpStatus.OK.value(),
                "User bookings retrieved successfully", bookings));
    }

    @Override
    public ResponseEntity<CommonResponse> updateBooking(Long bookingId, BookingRequest request) {
        Booking existingBooking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));


        existingBooking.setNumberOfPeople(request.getNumberOfPeople());
        existingBooking.setBookingDate(request.getBookingDate());
        existingBooking.setBookingTime(request.getBookingTime());
        existingBooking.setOfferId(request.getOfferId());

        Booking updatedBooking = bookingRepository.save(existingBooking);

        return ResponseEntity.ok(new CommonResponse(HttpStatus.OK.value(),
                "Booking updated successfully", updatedBooking));
    }

    @Override
    public ResponseEntity<CommonResponse> cancelBooking(Long bookingId,String reason) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        booking.setBookingStatus(BookingStatus.CANCELLED);
        booking.setCancellationReason(reason);
        bookingRepository.save(booking);

        return ResponseEntity.ok(new CommonResponse(HttpStatus.OK.value(),
                "Booking cancelled successfully", booking));
    }
}