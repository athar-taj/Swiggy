package cln.swiggy.restaurant.repository;

import cln.swiggy.restaurant.model.Offer;
import cln.swiggy.restaurant.model.Restaurant;
import cln.swiggy.restaurant.model.enums.OfferType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<Offer,Long> {
    List<Offer> findByRestaurantIdAndIsActiveTrueAndOfferType(Long restaurantId, OfferType offerType);

    List<Offer> findByRestaurantIdAndOfferType(Long restaurantId, OfferType offerType);

    List<Offer> findByRestaurantIdAndIsActiveTrue(Long restaurantId);

    List<Offer> findByRestaurantId(Long restaurantId);

    List<Offer> findByRestaurantAndIsActiveAndEndDateAfter(Restaurant restaurant, boolean b, LocalDateTime now);
}
