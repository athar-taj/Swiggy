package cln.swiggy.restaurant.repository;

import cln.swiggy.restaurant.model.Address;
import cln.swiggy.restaurant.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long>  {
    boolean existsByRestaurant(Restaurant restaurant);

    boolean existsByOutlet(String outlet);

    List<Address> findByRestaurant(Restaurant restaurant);

    List<Address> findAllByRestaurant(Restaurant restaurant);

    Address findByRestaurantId(Long id);
}
