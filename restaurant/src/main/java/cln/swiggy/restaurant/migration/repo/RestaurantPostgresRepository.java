package cln.swiggy.restaurant.migration.repo;

import cln.swiggy.restaurant.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantPostgresRepository extends JpaRepository<Restaurant, Long> {

    @Query("SELECT r.id FROM Restaurant r")
    List<Long> findAllIds();
}

