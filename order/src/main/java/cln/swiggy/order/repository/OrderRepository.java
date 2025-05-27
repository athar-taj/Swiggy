package cln.swiggy.order.repository;

import cln.swiggy.order.model.Order;
import cln.swiggy.order.model.enums.OrderStatus;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId, Sort sort);

    List<Order> findByRestaurantId(Long restaurantId, Sort sort);

    List<Order> findByRestaurantIdAndOrderStatus(Long restaurantId, OrderStatus status, Sort sort);
}
