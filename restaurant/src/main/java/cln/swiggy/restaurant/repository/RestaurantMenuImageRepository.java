package cln.swiggy.restaurant.repository;

import cln.swiggy.restaurant.model.Restaurant;
import cln.swiggy.restaurant.model.RestaurantMenuImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestaurantMenuImageRepository extends JpaRepository<RestaurantMenuImage, Long> {
    List<RestaurantMenuImage> findByRestaurant(Restaurant restaurant);
}
