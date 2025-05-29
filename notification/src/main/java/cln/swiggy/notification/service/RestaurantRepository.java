package cln.swiggy.notification.service;

import cln.swiggy.notification.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    List<Restaurant> findAllByName(String restaurantName);

    boolean existsByNameAndOutlet(String name, String outlet);
}
