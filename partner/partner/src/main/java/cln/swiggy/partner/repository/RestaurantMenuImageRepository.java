package cln.swiggy.partner.repository;

import cln.swiggy.partner.model.Restaurant;
import cln.swiggy.partner.model.RestaurantMenuImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestaurantMenuImageRepository extends JpaRepository<RestaurantMenuImage, Long> {
    List<RestaurantMenuImage> findByRestaurant(Restaurant restaurant);
}
