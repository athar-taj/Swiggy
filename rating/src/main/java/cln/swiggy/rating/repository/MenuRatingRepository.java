package cln.swiggy.rating.repository;

import cln.swiggy.rating.model.MenuRating;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuRatingRepository extends JpaRepository<MenuRating, Long> {
    List<MenuRating> findByUserId(Long userId);

    Optional<MenuRating> findByUserIdAndMenuId(@NotNull(message = "User ID cannot be null") Long userId, @NotNull(message = "Restaurant/Item ID cannot be null") Long elementId);
}
