package cln.swiggy.restaurant.repository;

import cln.swiggy.restaurant.model.Menu;
import cln.swiggy.restaurant.model.Restaurant;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findByRestaurantId(Long restaurantId);

    boolean existsByNameAndRestaurantAndPrice(String name, Restaurant restaurant,int price);
}
