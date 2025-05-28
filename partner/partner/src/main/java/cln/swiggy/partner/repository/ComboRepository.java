package cln.swiggy.partner.repository;

import cln.swiggy.partner.model.Combo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComboRepository extends JpaRepository<Combo, Long> {
    Optional<Combo> findByIdAndRestaurantId(Long comboId, Long restaurantId);

    List<Combo> findAllByRestaurantId(Long restaurantId);

    List<Combo> findAllByIsAvailableTrueOrderByTotalOrdersDesc();
}
