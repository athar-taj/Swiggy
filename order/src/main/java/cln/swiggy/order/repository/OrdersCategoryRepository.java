package cln.swiggy.order.repository;

import cln.swiggy.order.model.OrdersCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersCategoryRepository extends JpaRepository<OrdersCategory, Long> {
    boolean existsByName(String category);

    OrdersCategory findByName(String category);
}
