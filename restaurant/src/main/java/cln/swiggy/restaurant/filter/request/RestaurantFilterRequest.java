package cln.swiggy.restaurant.filter.request;


import cln.swiggy.restaurant.model.enums.SortOrder;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class RestaurantFilterRequest {

    private SortOrder costSortOrder;
    private Double minRating;
    private String categoryName;
    private List<String> facilityNames;
    private Double minCostForTwo;
    private Double maxCostForTwo;
    private Boolean isPureVeg;
}
