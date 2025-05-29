package cln.swiggy.partner.serviceImpl;

import cln.swiggy.partner.exception.ResourceNotFoundException;
import cln.swiggy.partner.model.Booking;
import cln.swiggy.partner.model.enums.BookingStatus;
import cln.swiggy.partner.model.response.CommonResponse;
import cln.swiggy.partner.repository.BookingRepository;
import cln.swiggy.partner.repository.RestaurantRepository;
import cln.swiggy.partner.service.BookingService;
import cln.swiggy.partner.serviceImpl.otherImple.NotificationUtil;
import com.aws.service.sns.service.SNSService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.routing-key}")
    private String routingKey;

    @Value("${rabbitmq.notification.user.exchange}")
    private String notificationExchange;

    @Value("${rabbitmq.notification.user.routing_Key}")
    private String notificationRoutingKey;


    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private RabbitTemplate rabbitTemplate;


    @Override
    public ResponseEntity<CommonResponse> confirmBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));
        booking.setBookingStatus(BookingStatus.CONFIRMED);
        bookingRepository.save(booking);

        String userEmail = (String) rabbitTemplate.convertSendAndReceive(exchange, "user_email_id_key", booking.getUserId());

        HashMap<String, Object> notificationData = NotificationUtil.getNotificationDataUser(booking.getRestaurant().getId() , booking.getUserId(), "USER","BOOKING_CONFIRM", LocalDateTime.now());
        rabbitTemplate.convertAndSend(notificationExchange, notificationRoutingKey, notificationData);

        return ResponseEntity.ok(new CommonResponse(HttpStatus.OK.value(),
                "Booking confirmed successfully", booking));
    }

    @Override
    public ResponseEntity<CommonResponse> getAllBookingsForRestaurant(Long restaurantId) {
        List<Booking> pendingBookings = bookingRepository.findByRestaurantIdAndBookingStatus(
                restaurantId,
                BookingStatus.PENDING
        );

        return ResponseEntity.ok(new CommonResponse(HttpStatus.OK.value(),
                "Pending bookings retrieved successfully", pendingBookings));
    }
}