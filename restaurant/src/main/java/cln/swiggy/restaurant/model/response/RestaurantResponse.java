package cln.swiggy.restaurant.model.response;

import cln.swiggy.restaurant.model.Restaurant;
import cln.swiggy.restaurant.model.enums.RestaurantType;
import lombok.Data;

import java.time.LocalTime;
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





    public static RestaurantResponse convertToResponse(Restaurant restaurant) {
        RestaurantResponse response = new RestaurantResponse();
        response.setName(restaurant.getName());
        response.setDescription(restaurant.getDescription());
        response.setIsAvailable(restaurant.getIsAvailable());

        Set<CategoryResponse> categoryResponses = restaurant.getCategories().stream()
                .map(CategoryResponse::convertToResponse)
                .collect(Collectors.toSet());
        response.setCategories(categoryResponses);

        Set<AddressResponse> addressResponses = restaurant.getAddresses().stream()
                .map(AddressResponse::convertToResponse)
                .collect(Collectors.toSet());
        response.setAddresses(addressResponses);

        return response;
    }
}
