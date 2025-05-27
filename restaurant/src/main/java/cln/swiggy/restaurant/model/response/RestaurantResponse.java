package cln.swiggy.restaurant.model.response;

import cln.swiggy.restaurant.model.Restaurant;
import cln.swiggy.restaurant.model.enums.RestaurantType;
import lombok.Data;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    private Set<CategoryResponse> categories;
    private Set<AddressResponse> addresses;
    private Set<OfferResponse> offers;
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

        Set<CategoryResponse> categoryResponses = restaurant.getCategories().stream()
                .map(CategoryResponse::convertToResponse)
                .collect(Collectors.toSet());
        response.setCategories(categoryResponses);

        Set<AddressResponse> addressResponses = restaurant.getAddresses().stream()
                .map(AddressResponse::convertToResponse)
                .collect(Collectors.toSet());
        response.setAddresses(addressResponses);

        Set<OfferResponse> offerResponses = restaurant.getOffers().stream()
                .map(OfferResponse::convertToResponse)
                .collect(Collectors.toSet());
        response.setOffers(offerResponses);


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
