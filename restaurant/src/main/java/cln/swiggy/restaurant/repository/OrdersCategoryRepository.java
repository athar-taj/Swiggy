package cln.swiggy.restaurant.repository;

import cln.swiggy.restaurant.model.OrdersCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrdersCategoryRepository extends JpaRepository<OrdersCategory, Long> {

    @Query(nativeQuery = true, value = "select * from orders_category order by total_orders desc limit ?;")
    List<String> getTopCategories(int limit);
}
