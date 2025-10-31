package cln.swiggy.restaurant.migration.repo;

import cln.swiggy.restaurant.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantPostgresRepository extends JpaRepository<Restaurant, Long> { }

