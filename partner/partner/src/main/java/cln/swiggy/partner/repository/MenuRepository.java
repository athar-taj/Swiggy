package cln.swiggy.partner.repository;

import cln.swiggy.partner.model.Menu;
import cln.swiggy.partner.model.Restaurant;
import jakarta.annotation.Nullable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    List<Menu> findByRestaurantId(Long restaurantId);

    boolean existsByNameAndRestaurantAndPrice(String name, Restaurant restaurant,int price);
}
