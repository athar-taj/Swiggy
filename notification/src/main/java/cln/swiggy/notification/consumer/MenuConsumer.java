package cln.swiggy.notification.consumer;

import cln.swiggy.notification.exception.ResourceNotFoundException;
import cln.swiggy.notification.model.Restaurant;
import cln.swiggy.notification.model.User;
import cln.swiggy.notification.service.AddressRepository;
import cln.swiggy.notification.service.NotificationService;
import cln.swiggy.notification.service.RestaurantRepository;
import cln.swiggy.notification.service.UserRepository;
import cln.swiggy.notification.serviceImpl.OtherService.ConvertToDateTime;
import com.aws.service.sns.service.SNSService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class MenuConsumer {

    @Autowired
    RestaurantRepository restaurantRepository;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    SNSService snsService;
    @Autowired
    NotificationService notificationService;

    @RabbitListener(queues = "user_notification_booking_queue")
    public void bookingNotification(HashMap<String, Object> data) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(Long.valueOf(data.get("restaurantId").toString()));
        Optional<User> user = userRepository.findById(Long.valueOf(data.get("userId").toString()));
        System.out.println("Received booking confirmation notification: " + data);

        if (restaurant.isEmpty()) {
            throw new ResourceNotFoundException("No restaurant found with id: " + data.get("restaurantId"));
        }

        Restaurant rest = restaurant.get();

        String subject = "Booking Confirmed for Restaurant: " + rest.getName();
        String body = "Hello, "+ user.get().getName() + "  Your booking at " + rest.getName() + " has been successfully confirmed. " +
                "We look forward to hosting you! If you have any questions or need to make adjustments, feel free to contact our support team. " +
                "Please check your booking details in your account dashboard for more information.";

        snsService.sendEmail(user.get().getEmail(), subject, body);

        Long userId = data.get("userId") != null ? Long.valueOf(data.get("userId").toString()) : null;

        notificationService.sendNotification(
                userId,
                data.get("ReceiverType").toString(),
                data.get("type").toString(),
                ConvertToDateTime.listToDateTime(data.get("time")),
                body,
                subject,
                "EMAIL"
        );
    }

    @RabbitListener(queues = "user_notification_offer_queue")
    public void offerNotification(HashMap<String, Object> data) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(Long.valueOf(data.get("restaurantId").toString()));
        List<User> users = userRepository.findAll();
        System.out.println("Received offer notification: " + data);

        if (restaurant.isEmpty()) {
            throw new ResourceNotFoundException("No restaurant found with id: " + data.get("restaurantId"));
        }

        Restaurant rest = restaurant.get();

        String subject = "Exciting Offer from Restaurant: " + rest.getName();
        String body = "Hello, " + rest.getName() + " is offering an exclusive deal just for you! " +
                "Don't miss out on this special offer. Visit our platform or restaurant's dashboard to explore and redeem it now. " +
                "Hurry, this offer is valid for a limited time only!";

        for (User user : users) {
            snsService.sendEmail(user.getEmail(), subject, body);

            notificationService.sendNotification(
                    user.getId(),
                    data.get("ReceiverType").toString(),
                    data.get("type").toString(),
                    ConvertToDateTime.listToDateTime(data.get("time")),
                    body,
                    subject,
                    "EMAIL"
            );
        }
    }

    @RabbitListener(queues = "user_notification_itemcombo_queue")
    public void itemComboNotification(HashMap<String, Object> data) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(Long.valueOf(data.get("restaurantId").toString()));
        List<User> users = userRepository.findAll();
        System.out.println("Received item combo notification: " + data);

        if (restaurant.isEmpty()) {
            throw new ResourceNotFoundException("No restaurant found with id: " + data.get("restaurantId"));
        }

        Restaurant rest = restaurant.get();

        String subject = "Exciting Item Combo Available at " + rest.getName();
        String body = "Hello, " + rest.getName() + " has an amazing new item combo just for you! " +
                "Check out our exclusive item combo offer now and enjoy a delightful dining experience. " +
                "Visit your dashboard or our restaurant to explore this combo before it's gone!";

        for (User user : users) {
            snsService.sendEmail(rest.getEmail(), subject, body);

            Long userId = data.get("restaurantId") != null ? Long.valueOf(data.get("restaurantId").toString()) : null;

            notificationService.sendNotification(
                    userId,
                    data.get("ReceiverType").toString(),
                    data.get("type").toString(),
                    ConvertToDateTime.listToDateTime(data.get("time")),
                    body,
                    subject,
                    "EMAIL"
            );
        }
    }
}
