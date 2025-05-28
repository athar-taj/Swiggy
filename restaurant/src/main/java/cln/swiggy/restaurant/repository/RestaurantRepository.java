package cln.swiggy.restaurant.repository;

import cln.swiggy.restaurant.model.Restaurant;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;


@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    List<Restaurant> findAll(@Nullable Specification<Restaurant> spec);

    List<Restaurant> findAllByNameAndIsAvailableTrue(String restaurantName);

    boolean existsByNameAndOutlet(String name, String outlet);

    @Query(nativeQuery = true, value = "SELECT * FROM restaurant ORDER BY created_at DESC LIMIT ?")
    List<Restaurant> getLatestRestaurants(int limit);


    @Query(value = """
        SELECT DISTINCT r.*
        FROM restaurant r
        LEFT JOIN restaurant_category rs ON rs.restaurant_id = r.id
        LEFT JOIN category c ON rs.category_id = c.id
        LEFT JOIN facility f ON r.id = f.restaurant_id
        LEFT JOIN menu m ON m.restaurant_id = r.id
        WHERE 
            LOWER(r.name) LIKE %:keyword% OR
            LOWER(r.outlet) LIKE %:keyword% OR
            LOWER(r.restaurant_type) LIKE %:keyword% OR
            LOWER(r.description) LIKE %:keyword% OR
            LOWER(c.description) LIKE %:keyword% OR
            LOWER(c.name) LIKE %:keyword% OR
            LOWER(f.facility_name) LIKE %:keyword% OR
            LOWER(f.description) LIKE %:keyword% OR
            LOWER(m.name) LIKE %:keyword% OR
            LOWER(m.description) LIKE %:keyword%
        """, nativeQuery = true)
    List<Restaurant> searchRestaurantsByKeyword(@Param("keyword") String keyword);

    List<Restaurant> findByAvgDeliveryTimeLessThanEqual(LocalTime avgDeliveryTime);
}
