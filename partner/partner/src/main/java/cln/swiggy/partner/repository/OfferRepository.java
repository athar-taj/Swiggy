package cln.swiggy.partner.repository;

import cln.swiggy.partner.model.Offer;
import cln.swiggy.partner.model.Restaurant;
import cln.swiggy.partner.model.enums.OfferType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<Offer,Long> {
    List<Offer> findByRestaurantIdAndIsActiveTrueAndOfferTypeAndEndDateAfter(Long restaurantId, OfferType offerType,LocalDateTime now);

    List<Offer> findByRestaurantIdAndOfferTypeAndEndDateAfter(Long restaurantId, OfferType offerType,LocalDateTime now);

    List<Offer> findByRestaurantIdAndIsActiveTrueAndEndDateAfter(Long restaurantId,LocalDateTime now);

    List<Offer> findByRestaurantIdAndEndDateAfter(Long restaurantId,LocalDateTime now);
}
