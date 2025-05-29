package cln.swiggy.notification.consumer;

import cln.swiggy.notification.exception.ResourceNotFoundException;
import cln.swiggy.notification.model.Address;
import cln.swiggy.notification.model.Restaurant;
import cln.swiggy.notification.model.User;
import cln.swiggy.notification.model.request.UserRequest;
import cln.swiggy.notification.service.AddressRepository;
import cln.swiggy.notification.service.NotificationService;
import cln.swiggy.notification.service.RestaurantRepository;
import cln.swiggy.notification.service.UserRepository;
import cln.swiggy.notification.serviceImpl.OtherService.CalculateDistance;
import cln.swiggy.notification.serviceImpl.OtherService.ConvertToDateTime;
import com.aws.service.sns.service.SNSService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantConsumer {

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


    @RabbitListener(queues = "restaurant_notification_queue")
    public void newRestaurantNotification(HashMap<String, Object> data) {
        System.out.println("Received new restaurant notification: " + data);
        List<Address> addresses = List.of();
        List<User> usersWithinRadius = new ArrayList<>();
        List<User> users = userRepository.findAll();

        Optional<Restaurant> restaurant = restaurantRepository.findById(Long.valueOf(data.get("restaurantId").toString()));
        if (restaurant.isPresent()) {
            addresses = addressRepository.findAllByRestaurant(restaurant.get());
        }

        if (addresses.isEmpty()) {
            System.out.println("No addresses found for the restaurant.");
        }
        Address newAddress = addresses.get(addresses.size() - 1);

        for (User user : users) {
            double distance = CalculateDistance.getDistance(user.getLatitude(), user.getLongitude(),
                    newAddress.getLatitude(), newAddress.getLongitude());
            if (distance <= 2.0) {
                usersWithinRadius.add(user);
            }
        }
        for (User user : usersWithinRadius) {
            String subject = "New Restaurant added: " + restaurant.get().getName();
            String body = "Hello "+ user.getName() + ", New Restaurant added: " + restaurant.get().getName();
            snsService.sendEmail(user.getEmail(),subject, body);

            Long restaurantId = data.get("restaurantId") != null ?
                    Long.valueOf(data.get("restaurantId").toString()) : null;

            notificationService.sendNotification(
                    restaurantId,
                    data.get("ReceiverType").toString(),
                    data.get("type").toString(),
                    ConvertToDateTime.listToDateTime(data.get("time")),
                    body,
                    subject,
                    "EMAIL"
            );
        }
    }

    @RabbitListener(queues = "restaurant_notification_order_queue")
    public void orderNotification(HashMap<String, Object> data) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(Long.valueOf(data.get("restaurantId").toString()));
        System.out.println("Received new Order notification: " + data);

        if (restaurant.isEmpty()) {
            throw new ResourceNotFoundException("No restaurant found with id: " + data.get("restaurantId"));
        }

        Restaurant rest = restaurant.get();

        String orderType = data.get("type").toString();
        String subject = "";
        String body = "";

        switch (orderType) {
            case "ORDER_PLACE":
                subject = "New Order Placed for Restaurant: " + rest.getName();
                body = "Hello, A new order has been placed at your restaurant, " + rest.getName() +
                        ". Please check your restaurant dashboard for order details.";
                break;

            case "ORDER_UPDATE":
                subject = "Order Updated for Restaurant: " + rest.getName();
                body = "Hello, An update has been made to an existing order at your restaurant, " + rest.getName() +
                        ". Please check your restaurant dashboard for updated order details.";
                break;

            case "CANCEL_ORDER":
                subject = "Order Cancelled for Restaurant: " + rest.getName();
                body = "Hello, An order at your restaurant, " + rest.getName() + ", has been cancelled. " +
                        "Please check your restaurant dashboard for more details.";
                break;

            default:
                throw new ResourceNotFoundException("Unknown order type: " + orderType);
        }

        snsService.sendEmail(rest.getEmail(), subject, body);

        Long restaurantId = data.get("restaurantId") != null ? Long.valueOf(data.get("restaurantId").toString()) : null;

        notificationService.sendNotification(
                restaurantId,
                data.get("ReceiverType").toString(),
                orderType,
                ConvertToDateTime.listToDateTime(data.get("time")),
                body,
                subject,
                "EMAIL"
        );
    }
    
    @RabbitListener(queues = "restaurant_notification_booking_queue")
    public void bookingNotification(HashMap<String, Object> data) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(Long.valueOf(data.get("restaurantId").toString()));
        System.out.println("Received new Booking notification: " + data);

        if (restaurant.isEmpty()) {
            throw new ResourceNotFoundException("No restaurant found with id: " + data.get("restaurantId"));
        }

        Restaurant rest = restaurant.get();

        String subject = "New Booking Received for Restaurant: " + rest.getName();
        String body = "Hello, A new booking has been made for your restaurant, " + rest.getName() +
                ". Please check your restaurant dashboard for booking details.";

        snsService.sendEmail(rest.getEmail(), subject, body);

        Long restaurantId = data.get("restaurantId") != null ? Long.valueOf(data.get("restaurantId").toString()) : null;

        notificationService.sendNotification(
                restaurantId,
                data.get("ReceiverType").toString(),
                data.get("type").toString(),
                ConvertToDateTime.listToDateTime(data.get("time")),
                body,
                subject,
                "EMAIL"
        );
    }

    @RabbitListener(queues = "restaurant_notification_payment_queue")
    public void paymentNotification(HashMap<String, Object> data) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(Long.valueOf(data.get("restaurantId").toString()));
        System.out.println("Received new Payment notification: " + data);

        if (restaurant.isEmpty()) {
            throw new ResourceNotFoundException("No restaurant found with id: " + data.get("restaurantId"));
        }

        Restaurant rest = restaurant.get();

        String subject = "Payment Received for Restaurant: " + rest.getName();
        String body = "Hello, Your restaurant, " + rest.getName() + ", has received a payment. " +
                "Please check your restaurant dashboard for payment details.";

        snsService.sendEmail(rest.getEmail(), subject, body);

        Long restaurantId = data.get("restaurantId") != null ? Long.valueOf(data.get("restaurantId").toString()) : null;
        notificationService.sendNotification(
                restaurantId,
                data.get("ReceiverType").toString(),
                data.get("type").toString(),
                ConvertToDateTime.listToDateTime(data.get("time")),
                body,
                subject,
                "EMAIL"
        );
    }

    @RabbitListener(queues = "restaurant_notification_rating_queue")
    public void ratingNotification(HashMap<String, Object> data) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(Long.valueOf(data.get("restaurantId").toString()));
        System.out.println("Received new Rating notification: " + data);

        if (restaurant.isEmpty()) {
            throw new ResourceNotFoundException("No restaurant found with id: " + data.get("restaurantId"));
        }

        Restaurant rest = restaurant.get();

        String subject = "New Rating Received for Restaurant: " + rest.getName();
        String body = "Hello, Your restaurant, " + rest.getName() + ", has received a new rating: " + rest.getRating() + " stars. " +
                "The restaurant's total rating is now: " + rest.getTotalRating() + " stars. " +
                "Please check your restaurant dashboard for more details.";

        snsService.sendEmail(rest.getEmail(), subject, body);

        Long restaurantId = data.get("restaurantId") != null ? Long.valueOf(data.get("restaurantId").toString()) : null;

        notificationService.sendNotification(
                restaurantId,
                data.get("ReceiverType").toString(),
                data.get("type").toString(),
                ConvertToDateTime.listToDateTime(data.get("time")),
                body,
                subject,
                "EMAIL"
        );
    }

}
