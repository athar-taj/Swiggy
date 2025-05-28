package cln.swiggy.restaurant.repository;

import cln.swiggy.restaurant.model.Menu;
import cln.swiggy.restaurant.model.Restaurant;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findAll(@Nullable Specification<Menu> spec);

    boolean existsByNameAndRestaurantAndPrice(String name, Restaurant restaurant,int price);

    @Query(value = """
    SELECT DISTINCT m.*
    FROM menu m
    LEFT JOIN category c ON m.category_id = c.id
    WHERE 
        LOWER(c.description) LIKE %:keyword% OR
        LOWER(c.name) LIKE %:keyword% OR
        LOWER(m.name) LIKE %:keyword% OR
        LOWER(m.menu_type) LIKE %:keyword% OR
        LOWER(m.description) LIKE %:keyword%
    """, nativeQuery = true)
    List<Menu> searchMenuByKeyword(@Param("keyword") String keyword);


    @Query(value = "SELECT * FROM menu ORDER BY total_orders DESC LIMIT ?1;", nativeQuery = true)
    List<Menu> findByTotalOrderOrderByDesc(int limit);

    @Query(value = "select menu_id from orders where user_id= ?1;", nativeQuery = true)
    List<Long> getMenuIdUserOrders(Long userId);

    List<Menu> findByCategoryId(Long category);

    List<Menu> findByRestaurantId(Long restaurantId);
}
