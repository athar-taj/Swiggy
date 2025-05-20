package cln.swiggy.restaurant.repository;

import cln.swiggy.restaurant.model.Restaurant;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    List<Restaurant> findAllByName(String restaurantName);

    boolean existsByNameAndOutlet(String name, String outlet);
}
