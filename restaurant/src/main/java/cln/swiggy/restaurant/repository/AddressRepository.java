package cln.swiggy.restaurant.repository;

import cln.swiggy.restaurant.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    boolean existsByRestaurantId(Long restaurantId);

    boolean existsByOutlet(String outlet);

    List<Address> findByRestaurantId(Long restaurantId);

    List<Address> findAllByRestaurantId(Long restaurantId);
}
