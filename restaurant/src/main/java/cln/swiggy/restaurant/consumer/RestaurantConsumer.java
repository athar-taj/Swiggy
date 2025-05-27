package cln.swiggy.restaurant.consumer;

import cln.swiggy.restaurant.model.Restaurant;
import cln.swiggy.restaurant.repository.RestaurantRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
public class RestaurantConsumer {

    @Autowired
    RestaurantRepository restaurantRepository;

    @RabbitListener(queues = "is_restaurant_exists_for_order")
    public Boolean isRestaurantExistsForOrder(Long restaurantId) {
        return restaurantRepository.existsById(restaurantId);
    }

    @RabbitListener(queues = "update_restaurant_rating")
    public Boolean updateRestaurantRating(HashMap<String, Object> data) {

        Optional<Restaurant> restaurant = restaurantRepository.findById(Long.valueOf(data.get("restaurantId").toString()));
        if (restaurant.isPresent()) {
            Restaurant rest = restaurant.get();
            rest.setTotalRating(rest.getTotalRating() + 1);


            double newRatingValue = Double.parseDouble(data.get("rating").toString());
            double currentAvgRating = rest.getRating() != null ? rest.getRating() : 0.0;
            double newAvgRating = ((currentAvgRating * (rest.getTotalRating() - 1)) + newRatingValue) / rest.getTotalRating();

            newAvgRating = Math.round(newAvgRating * 10.0) / 10.0;

            rest.setRating(newAvgRating);
            restaurantRepository.save(rest);
            return true;
        }
        return false;
    }
}
