package cln.swiggy.restaurant.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RestaurantDistanceOffer {
    private Restaurant restaurant;
    private double distance;
    private double bestDiscount;
    private List<Offer> activeOffers;
}
