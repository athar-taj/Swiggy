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
import cln.swiggy.restaurant.serviceImpl.otherImple.NotificationUtil;
//import com.aws.service.sns.service.SNSService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;

@Service
public class BookingServiceImpl implements BookingService {

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.routing-key}")
    private String routingKey;

    @Value("${rabbitmq.notification.restaurant.exchange}")
    private String notificationExchange;

    @Value("${rabbitmq.notification.restaurant.routing_Key}")
    private String notificationRoutingKey;

    @Autowired private BookingRepository bookingRepository;
    @Autowired private RestaurantRepository restaurantRepository;
    @Autowired private RabbitTemplate rabbitTemplate;
//    @Autowired private SNSService snsService;

    @Override public ResponseEntity<CommonResponse> bookTable(BookingRequest request) {
        Booking booking = new Booking();
        Restaurant restaurant = restaurantRepository.findById(request.getRestaurantId())
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found"));
        Boolean isOwnerExists = (Boolean) rabbitTemplate.convertSendAndReceive(exchange, routingKey, request.getUserId());

        if (Boolean.FALSE.equals(isOwnerExists)) throw new ResourceNotFoundException("Owner not found with id: " + request.getUserId());

        booking.setUserId(request.getUserId());
        booking.setRestaurant(restaurant);
        booking.setNumberOfPeople(request.getNumberOfPeople());
        booking.setBookingDate(request.getBookingDate());
        booking.setBookingTime(request.getBookingTime());
        booking.setBookingStatus(BookingStatus.PENDING);
        booking.setOfferId(request.getOfferId());
        booking.setCreatedAt(LocalDateTime.now());
        Booking savedBooking = bookingRepository.save(booking);
        HashMap<String, Object> notificationData = NotificationUtil.getNotificationData(restaurant.getId(), "RESTAURANT","TABLE_BOOK", LocalDateTime.now());
        rabbitTemplate.convertAndSend(notificationExchange, notificationRoutingKey, notificationData);
        return ResponseEntity.ok(new CommonResponse( HttpStatus.OK.value(), "Booking created successfully", savedBooking));
    }

    @Cacheable(value = "booking_by_id", key = "#bookingId")
    @Override
    public ResponseEntity<CommonResponse> getBookingDetails(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        return ResponseEntity.ok(new CommonResponse(HttpStatus.OK.value(),
                "Booking details retrieved successfully", booking));
    }

    @Cacheable(value = "user_bookings", key = "#userId")
    @Override
    public ResponseEntity<CommonResponse> getAllBookingsForUser(Long userId) {
        List<Booking> bookings = bookingRepository.findByUserId(userId);

        return ResponseEntity.ok(new CommonResponse(HttpStatus.OK.value(),
                "User bookings retrieved successfully", bookings));
    }

    @Caching(evict = {
            @CacheEvict(value = "booking_by_id", key = "#bookingId"),
            @CacheEvict(value = "user_bookings", allEntries = true)
    })
    @Override
    public ResponseEntity<CommonResponse> updateBooking(Long bookingId, BookingRequest request) {
        Booking existingBooking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        existingBooking.setNumberOfPeople(request.getNumberOfPeople());
        existingBooking.setBookingDate(request.getBookingDate());
        existingBooking.setBookingTime(request.getBookingTime());
        existingBooking.setOfferId(request.getOfferId());

        Booking updatedBooking = bookingRepository.save(existingBooking);

        HashMap<String, Object> notificationData = NotificationUtil.getNotificationData(
                existingBooking.getRestaurant().getId(), "RESTAURANT", "BOOKING_UPDATE", LocalDateTime.now());
        rabbitTemplate.convertAndSend(notificationExchange, notificationRoutingKey, notificationData);

        return ResponseEntity.ok(new CommonResponse(HttpStatus.OK.value(),
                "Booking updated successfully", updatedBooking));
    }

    @Caching(evict = {
            @CacheEvict(value = "booking_by_id", key = "#bookingId"),
            @CacheEvict(value = "user_bookings", allEntries = true)
    })
    @Override
    public ResponseEntity<CommonResponse> cancelBooking(Long bookingId, String reason) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        booking.setBookingStatus(BookingStatus.CANCELLED);
        booking.setCancellationReason(reason);
        bookingRepository.save(booking);

        HashMap<String, Object> notificationData = NotificationUtil.getNotificationData(
                booking.getRestaurant().getId(), "RESTAURANT", "BOOKING_CANCEL", LocalDateTime.now());
        rabbitTemplate.convertAndSend(notificationExchange, notificationRoutingKey, notificationData);

        return ResponseEntity.ok(new CommonResponse(HttpStatus.OK.value(),
                "Booking cancelled successfully", booking));
    }
}
