package cln.swiggy.restaurant.filter.request;

import cln.swiggy.restaurant.model.enums.MenuType;
import cln.swiggy.restaurant.model.enums.SortOrder;
import lombok.Data;


@Data
public class MenuFilterRequest {

        private Boolean ratingSort;
        private Boolean fastDelivery;
        private Boolean latestItem;
        private Boolean isOffer;

        private SortOrder costSortOrder;
        private Double minRating;
        private String categoryName;
        private MenuType menuType;

}
