package cln.swiggy.restaurant.filter.Implementation;

import cln.swiggy.restaurant.filter.request.RestaurantFilterRequest;
import cln.swiggy.restaurant.model.Restaurant;
import cln.swiggy.restaurant.model.enums.RestaurantType;
import cln.swiggy.restaurant.model.enums.SortOrder;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class RestaurantFilter {

    public static Specification<Restaurant> withFilters(RestaurantFilterRequest filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getMinRating() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("rating"), filter.getMinRating()));
            }

            if (filter.getCategoryName() != null) {
                Join<Object, Object> categories = root.join("categories", JoinType.INNER);
                predicates.add(cb.equal(cb.lower(categories.get("name")), filter.getCategoryName().toLowerCase()));
            }

            if (filter.getFacilityNames() != null && !filter.getFacilityNames().isEmpty()) {
                Join<Object, Object> facilities = root.join("facilities", JoinType.INNER);
                predicates.add(facilities.get("facilityName").in(filter.getFacilityNames()));
            }

            if (filter.getMinCostForTwo() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("costForTwo"), filter.getMinCostForTwo()));
            }

            if (filter.getMaxCostForTwo() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("costForTwo"), filter.getMaxCostForTwo()));
            }

            if (filter.getIsPureVeg() != null) {
                predicates.add(cb.equal(root.get("restaurantType"), filter.getIsPureVeg() ? RestaurantType.VEG : RestaurantType.NON_VEG));
            }

            if (filter.getCostSortOrder() != null) {
                if (filter.getCostSortOrder() == SortOrder.LOW_TO_HIGH) {
                    query.orderBy(cb.asc(root.get("costForTwo")));
                }else {
                    query.orderBy(cb.desc(root.get("costForTwo")));
                }
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
