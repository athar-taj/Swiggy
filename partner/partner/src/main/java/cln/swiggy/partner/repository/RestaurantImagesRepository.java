package cln.swiggy.partner.repository;

import cln.swiggy.partner.model.RestaurantImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantImagesRepository extends JpaRepository<RestaurantImages, Long> {
}
