package cln.swiggy.partner.repository;

import cln.swiggy.partner.model.Address;
import cln.swiggy.partner.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    boolean existsByRestaurant(Restaurant restaurant);

    boolean existsByOutlet(String outlet);

    List<Address> findByRestaurant(Restaurant restaurant);

    List<Address> findAllByRestaurant(Restaurant restaurant);
}
