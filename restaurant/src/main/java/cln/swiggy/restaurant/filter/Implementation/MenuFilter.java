package cln.swiggy.restaurant.filter.Implementation;

import cln.swiggy.restaurant.filter.request.MenuFilterRequest;
import cln.swiggy.restaurant.model.Menu;
import cln.swiggy.restaurant.model.enums.MenuType;
import cln.swiggy.restaurant.model.enums.RestaurantType;
import cln.swiggy.restaurant.model.enums.SortOrder;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class MenuFilter {

    public static Specification<Menu> withFilters(MenuFilterRequest filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getMinRating() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("rating"), filter.getMinRating()));
            }

            if (filter.getCategoryName() != null) {
                Join<Object, Object> categories = root.join("categories", JoinType.INNER);
                predicates.add(cb.equal(cb.lower(categories.get("name")), filter.getCategoryName().toLowerCase()));
            }


            if (filter.getMenuType() != null) {
                switch (filter.getMenuType()) {
                    case VEG:
                        predicates.add(cb.equal(root.get("menuType"), RestaurantType.VEG));
                        break;
                    case NON_VEG:
                        predicates.add(cb.equal(root.get("menuType"), RestaurantType.NON_VEG));
                        break;
                }
            }

//            if (Boolean.TRUE.equals(filter.getIsOffer())) {
//                predicates.add(cb.isNotNull(root.get("offer")));
//            }

            // Assuming 30 mins preparation time for delivery is Fast
            if (Boolean.TRUE.equals(filter.getFastDelivery())) {
                Join<Object, Object> deliveryTime = root.join("restaurant", JoinType.INNER);
                LocalTime thirtyMinutes = LocalTime.of(0, 30);
                predicates.add(cb.lessThanOrEqualTo(deliveryTime.get("avgDeliveryTime"), thirtyMinutes));
            }

            List<Order> orders = new ArrayList<>();

            if (Boolean.TRUE.equals(filter.getRatingSort())) {
                orders.add(cb.desc(root.get("rating")));
            }

            if (Boolean.TRUE.equals(filter.getLatestItem())) {
                orders.add(cb.desc(root.get("createdAt")));
            }

            if (filter.getCostSortOrder() != null) {
                if (filter.getCostSortOrder() == SortOrder.LOW_TO_HIGH) {
                    orders.add(cb.asc(root.get("price")));
                } else if (filter.getCostSortOrder() == SortOrder.HIGH_TO_LOW) {
                    orders.add(cb.desc(root.get("price")));
                }
            }

            if (!orders.isEmpty()) {
                query.orderBy(orders);
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
