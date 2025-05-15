package cln.swiggy.restaurant.repository;

import cln.swiggy.restaurant.model.RestaurantImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantImagesRepository extends JpaRepository<RestaurantImages, Long> {
}
