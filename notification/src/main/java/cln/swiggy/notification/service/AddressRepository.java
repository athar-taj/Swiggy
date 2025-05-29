package cln.swiggy.notification.service;

import cln.swiggy.notification.model.Address;
import cln.swiggy.notification.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findAllByRestaurant(Restaurant restaurant);
}
