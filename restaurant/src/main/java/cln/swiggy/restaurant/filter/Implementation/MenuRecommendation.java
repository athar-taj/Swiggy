package cln.swiggy.restaurant.filter.Implementation;

import cln.swiggy.restaurant.model.Address;
import cln.swiggy.restaurant.model.Menu;
import cln.swiggy.restaurant.model.response.CommonResponse;
import cln.swiggy.restaurant.model.response.MenuResponse;
import cln.swiggy.restaurant.repository.AddressRepository;
import cln.swiggy.restaurant.repository.MenuImagesRepository;
import cln.swiggy.restaurant.repository.MenuRepository;
import cln.swiggy.restaurant.serviceImpl.RestaurantServiceImpl;
import cln.swiggy.restaurant.serviceImpl.otherImple.CalculateDistance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MenuRecommendation {

    @Autowired MenuRepository menuRepository;
    @Autowired MenuImagesRepository menuImagesRepository;
    @Autowired AddressRepository addressRepository;

    public ResponseEntity<CommonResponse> recommendMenuByMostOrders(int limit) {
        List<Menu> menus = menuRepository.findByTotalOrderOrderByDesc(limit);
        List<MenuResponse> responseData = MenuResponse.convertToResponse(menus,menuImagesRepository);
        return ResponseEntity.ok(new CommonResponse(200, "Most Ordered Menus Fetched Successfully", responseData));
    }

    public ResponseEntity<CommonResponse> recommendMenuByUserOrders(Long userId){
        List<Long> menuIds = menuRepository.getMenuIdUserOrders(userId);

        ArrayList<Long> menuCategory = new ArrayList<>();

        for (Long menuId : menuIds) {
            Optional<Menu> menu = menuRepository.findById(menuId);
            menu.ifPresent(value -> menuCategory.add(value.getCategory().getId()));
        }

        List<Menu> menus = new ArrayList<>();
        for (Long category : menuCategory) {
            List<Menu> m = menuRepository.findByCategoryId(category);
            menus.addAll(m);
        }

        return ResponseEntity.ok(new CommonResponse(200, "Recommended Menus Fetched Successfully", menus));
    }

    public ResponseEntity<CommonResponse> recommendByFastDelivery(Double lat, Double lng){
        int searchRadius = 7;

        List<RestaurantServiceImpl.RestaurantDistance> nearbyRestaurants = new ArrayList<>();

        List<Address> restaurantsAddress = addressRepository.findAll();
        for(Address restaurant : restaurantsAddress) {
            double distance = CalculateDistance.getDistance(
                    lat,
                    lng,
                    restaurant.getLatitude(),
                    restaurant.getLongitude()
            );
            if (distance <= searchRadius) {
                nearbyRestaurants.add(new RestaurantServiceImpl.RestaurantDistance(restaurant.getRestaurant(), distance));
            }
        }

        nearbyRestaurants.sort(Comparator.comparingDouble(RestaurantServiceImpl.RestaurantDistance::getDistance));
        List<RestaurantServiceImpl.RestaurantDistance> limitedRestaurants = nearbyRestaurants.stream()
                .filter(r -> r.getRestaurant().getAvgDeliveryTime().getMinute() <= 15 )
                .limit(10)
                .collect(Collectors.toList());


        return ResponseEntity.ok(new CommonResponse(200, "Recommended Restaurants Fetched Successfully", limitedRestaurants));
    }

}
