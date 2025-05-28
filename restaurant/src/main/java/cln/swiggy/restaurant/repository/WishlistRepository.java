package cln.swiggy.restaurant.repository;


import cln.swiggy.restaurant.model.Menu;
import cln.swiggy.restaurant.model.Wishlist;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    List<Wishlist> findByUserId(Long userId);

    Optional<Wishlist> findByIdAndUserId(Long itemId, Long userId);

    boolean existsByUserIdAndMenu(Long userId, Menu menu);
}
