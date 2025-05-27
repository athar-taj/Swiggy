package cln.swiggy.rating.repository;

import cln.swiggy.rating.model.RestaurantRating;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRatingRepository extends JpaRepository<RestaurantRating, Long> {
    Optional<RestaurantRating> findByUserIdAndRestaurantId(@NotNull(message = "User ID cannot be null") Long userId, Long restaurantId);

    List<RestaurantRating> findByUserId(Long userId);
}
