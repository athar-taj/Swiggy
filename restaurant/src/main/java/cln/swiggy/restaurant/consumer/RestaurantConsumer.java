package cln.swiggy.restaurant.consumer;

import cln.swiggy.restaurant.repository.RestaurantRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RestaurantConsumer {

    @Autowired
    RestaurantRepository restaurantRepository;

    @RabbitListener(queues = "is_restaurant_exists_for_order")
    public Boolean isRestaurantExistsForOrder(Long restaurantId) {
        return restaurantRepository.existsById(restaurantId);
    }
}
