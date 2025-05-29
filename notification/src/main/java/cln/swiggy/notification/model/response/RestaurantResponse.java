package cln.swiggy.notification.model.response;


import cln.swiggy.notification.model.Restaurant;
import cln.swiggy.notification.model.enums.RestaurantType;
import lombok.Data;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class RestaurantResponse {

    private Long ownerId;
    private String name;
    private String contactNo;
    private String email;
    private String outlet;
    private String description;
    private String logo;
    private RestaurantType restaurantType;
    private List<String> openDays;
    private LocalTime startTime;
    private LocalTime endTime;
    private Boolean isAvailable;
    private LocalTime avgDeliveryTime;
    private Double costForTwo;
    private int totalRating;
    private Double rating;



    public static RestaurantResponse convertToResponse(Restaurant restaurant) {
        RestaurantResponse response = new RestaurantResponse();
        response.setName(restaurant.getName());
        response.setDescription(restaurant.getDescription());
        response.setLogo(restaurant.getLogo());
        response.setOutlet(restaurant.getOutlet());
        response.setContactNo(restaurant.getContactNo());
        response.setEmail(restaurant.getEmail());
        response.setRestaurantType(restaurant.getRestaurantType());
        response.setOpenDays(restaurant.getOpenDays());
        response.setStartTime(restaurant.getStartTime());
        response.setEndTime(restaurant.getEndTime());
        response.setAvgDeliveryTime(restaurant.getAvgDeliveryTime());
        response.setCostForTwo(restaurant.getCostForTwo());
        response.setTotalRating(restaurant.getTotalRating());
        response.setRating(restaurant.getRating());
        response.setIsAvailable(restaurant.getIsAvailable());

        return response;
    }

    public static List<RestaurantResponse> convertToResponse(List<Restaurant> restaurants){
        List<RestaurantResponse> response = new ArrayList<>();
        for (Restaurant restaurant : restaurants){
            response.add(convertToResponse(restaurant));
        }
        return response;
    }
}
