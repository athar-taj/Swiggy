package cln.swiggy.restaurant.serviceImpl;

import cln.swiggy.restaurant.exception.ResourceNotFoundException;
import cln.swiggy.restaurant.filter.Implementation.RestaurantFilter;
import cln.swiggy.restaurant.filter.request.RestaurantFilterRequest;
import cln.swiggy.restaurant.model.*;
import cln.swiggy.restaurant.model.response.CommonResponse;
import cln.swiggy.restaurant.model.response.RestaurantResponse;
import cln.swiggy.restaurant.repository.*;
import cln.swiggy.restaurant.service.RestaurantService;
import cln.swiggy.restaurant.serviceImpl.otherImple.CalculateDistance;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.routing-key}")
    private String routingKey;

    @Autowired  RestaurantRepository restaurantRepository;
    @Autowired  AddressRepository addressRepository;
    @Autowired  OfferRepository offerRepository;
    @Autowired  RedisService redisService;

    @Override
    public ResponseEntity<CommonResponse> getAllRestaurants() {
        CommonResponse cachedResponse = redisService.get("restaurants_all", CommonResponse.class);
        if (cachedResponse != null) {
            return ResponseEntity.ok(cachedResponse);
        }
        List<Restaurant> restaurants = restaurantRepository.findAll();

        CommonResponse response;
        if (restaurants.isEmpty()) {
            response = new CommonResponse(200, "No restaurants found", new ArrayList<>());
        } else {
            List<RestaurantResponse> responses = restaurants.stream()
                    .map(RestaurantResponse::convertToResponse)
                    .collect(Collectors.toList());
            response = new CommonResponse(200, "Restaurants retrieved successfully", responses);
        }

        redisService.set("restaurants_all", response, 1800L);

        return ResponseEntity.ok(response);
    }

    @Override
    @Cacheable(value = "restaurant_by_id", key = "#restaurantId")
    public ResponseEntity<CommonResponse> getRestaurantById(Long restaurantId) throws ResourceNotFoundException {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + restaurantId));

        return ResponseEntity.ok(new CommonResponse(200, "Restaurant retrieved successfully",
                RestaurantResponse.convertToResponse(restaurant)));
    }

    @Override
    public ResponseEntity<CommonResponse> getRestaurantAvailability(Long restaurantId) {
            Restaurant restaurant = restaurantRepository.findById(restaurantId)
                    .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + restaurantId));

            Map<String, Object> availabilityInfo = new HashMap<>();
            availabilityInfo.put("isAvailable", restaurant.getIsAvailable());
            availabilityInfo.put("startTime", restaurant.getStartTime());
            availabilityInfo.put("endTime", restaurant.getEndTime());
            availabilityInfo.put("openDays", restaurant.getOpenDays());

            return ResponseEntity.ok(new CommonResponse(200, "Restaurant availability retrieved successfully",
                    availabilityInfo));

    }

    @Override
    @CachePut(value = "restaurant_by_id", key = "#restaurantId")
    @CacheEvict(value = "restaurants_all", key = "'all_restaurants'")
    public ResponseEntity<CommonResponse> updateRestaurantAvailability(Long restaurantId, Boolean available) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + restaurantId));

        restaurant.setIsAvailable(available);
        restaurant.setUpdatedAt(LocalDateTime.now());
        Restaurant updatedRestaurant = restaurantRepository.save(restaurant);

        Map<String, Object> availabilityInfo = new HashMap<>();
        availabilityInfo.put("isAvailable", updatedRestaurant.getIsAvailable());
        availabilityInfo.put("restaurantId", updatedRestaurant.getId());
        availabilityInfo.put("restaurantName", updatedRestaurant.getName());

        return ResponseEntity.ok(new CommonResponse(200,
                "Restaurant availability " + (available ? "enabled" : "disabled") + " successfully",
                availabilityInfo));
    }

    @Override
    public ResponseEntity<CommonResponse> getRestaurantOutlets(String restaurantName) throws ResourceNotFoundException {
        List<Restaurant> restaurants = restaurantRepository.findAllByNameAndIsAvailableTrue(restaurantName);
        if (restaurants.isEmpty()) {
            throw new ResourceNotFoundException("Restaurant not found with name: " + restaurantName);
        }
        List<String> outlets = restaurants.stream()
                .map(Restaurant::getOutlet)
                .distinct()
                .collect(Collectors.toList());
        return ResponseEntity.ok(new CommonResponse(200, "Outlets retrieved successfully", outlets));
    }

    @Override
    @Cacheable(value = "restaurant_categories", key = "#restaurantId")
    public ResponseEntity<CommonResponse> getRestaurantCategories(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + restaurantId));
        List<String> categories = restaurant.getCategories().stream()
                .map(Category::getName)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new CommonResponse(200, "Categories retrieved successfully", categories));
    }

    @Override
    public ResponseEntity<CommonResponse> getRestaurantMenus(Long restaurantId) throws ResourceNotFoundException {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + restaurantId));
        List<String> menus = restaurant.getMenus().stream()
                .map(menu -> menu.getName() + " - " + menu.getPrice())
                .collect(Collectors.toList());
        return ResponseEntity.ok(new CommonResponse(200, "Menus retrieved successfully", menus));
    }

    @Override
    public ResponseEntity<CommonResponse> nearestRestaurants(Double lat, Double lng, Integer radius) {

        int searchRadius = (radius != null) ? radius : 5;

        List<Address> restaurantsAddress = addressRepository.findAll();
        if (restaurantsAddress.isEmpty()) {
            return ResponseEntity.ok(new CommonResponse(200, "No restaurants found", new ArrayList<>()));
        }

        List<RestaurantDistance> nearbyRestaurants = new ArrayList<>();

        for(Address restaurant : restaurantsAddress) {
            double distance = CalculateDistance.getDistance(
                    lat,
                    lng,
                    restaurant.getLatitude(),
                    restaurant.getLongitude()
            );
            System.out.println(distance);
            if (distance <= searchRadius) {
                nearbyRestaurants.add(new RestaurantDistance(restaurant.getRestaurant(), distance));
            }
        }

        nearbyRestaurants.sort(Comparator.comparingDouble(RestaurantDistance::getDistance));
        List<RestaurantDistance> limitedRestaurants = nearbyRestaurants.stream()
                .limit(10)   
                .collect(Collectors.toList());

        List<Map<String, Object>> response = limitedRestaurants.stream()
                .map(rd -> {
                    Map<String, Object> restaurantInfo = new HashMap<>();
                    restaurantInfo.put("restaurant", rd.getRestaurant());
                    restaurantInfo.put("distance", String.format("%.1f km", rd.getDistance()));
                    return restaurantInfo;
                })
                .collect(Collectors.toList());

        String message = nearbyRestaurants.isEmpty()
                ? "No restaurants found within " + searchRadius + " km radius"
                : "Found " + nearbyRestaurants.size() + " restaurants within " + searchRadius + " km radius";

        return ResponseEntity.ok(new CommonResponse(200, message, response));
    }

    @Getter
    public static class RestaurantDistance {
        public final Restaurant restaurant;
        public final double distance;

        public RestaurantDistance(Restaurant restaurant, double distance) {
            this.restaurant = restaurant;
            this.distance = distance;
        }
    }

    @Override
    public ResponseEntity<CommonResponse> nearestRestaurantsWithOffers(Double lat, Double lng, Integer radius) {
        int searchRadius = (radius != null) ? radius : 5;

        List<Address> restaurantsAddress = addressRepository.findAll();
        if (restaurantsAddress.isEmpty()) {
            return ResponseEntity.ok(new CommonResponse(200, "No restaurants found", new ArrayList<>()));
        }

        List<RestaurantDistanceOffer> nearbyRestaurants = new ArrayList<>();

        for (Address address : restaurantsAddress) {
            double distance = CalculateDistance.getDistance(
                    lat,
                    lng,
                    address.getLatitude(),
                    address.getLongitude()
            );

            if (distance <= searchRadius) {
                Restaurant restaurant = address.getRestaurant();
                List<Offer> activeOffers = offerRepository.findByRestaurantAndIsActiveAndEndDateAfter(
                        restaurant,
                        true,
                        LocalDateTime.now()
                );

                Double bestDiscount = activeOffers.stream()
                        .map(Offer::getOfferDiscount)
                        .max(Double::compareTo)
                        .orElse(0.0);

                nearbyRestaurants.add(new RestaurantDistanceOffer(restaurant, distance, bestDiscount, activeOffers));
            }
        }

        List<RestaurantDistanceOffer> limitedRestaurants = nearbyRestaurants.stream()
                .limit(10)
                .collect(Collectors.toList());

        List<Map<String, Object>> response = limitedRestaurants.stream()
                .map(rd -> {
                    Map<String, Object> restaurantInfo = new HashMap<>();
                    restaurantInfo.put("restaurant", rd.getRestaurant());
                    restaurantInfo.put("distance", String.format("%.1f km", rd.getDistance()));
                    restaurantInfo.put("activeOffers", offerRepository.findByRestaurantId(rd.getRestaurant().getId()));
                    return restaurantInfo;
                })
                .collect(Collectors.toList());

        String message = nearbyRestaurants.isEmpty()
                ? "No restaurants found within " + searchRadius + " km radius"
                : "Found " + nearbyRestaurants.size() + " restaurants within " + searchRadius + " km radius";

        return ResponseEntity.ok(new CommonResponse(200, message, response));
    }

    @Override
    public ResponseEntity<CommonResponse> newlyRegisteredRestaurants(Integer limit) {
        int resultLimit = (limit != null) ? limit : 5;

        List<Restaurant> newRestaurants = restaurantRepository.getLatestRestaurants(resultLimit);

        if (newRestaurants.isEmpty()) {
            return ResponseEntity.ok(new CommonResponse(200, "No restaurants found", new ArrayList<>()));
        }

        List<RestaurantResponse> responses = newRestaurants.stream()
                .map(RestaurantResponse::convertToResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new CommonResponse(200,
                "Recently registered restaurants retrieved successfully", responses));
    }

    public ResponseEntity<CommonResponse> filterRestaurants(RestaurantFilterRequest request) {
        Specification<Restaurant> specification = RestaurantFilter.withFilters(request);

        List<Restaurant> filteredRestaurant = restaurantRepository.findAll(specification);
        return ResponseEntity.ok(new CommonResponse(200,"Product Fetched Successfully",RestaurantResponse.convertToResponse(filteredRestaurant)));
    }

    @Override
    public ResponseEntity<CommonResponse> getFastDeliveryRestaurants() {
        LocalTime avgDeliveryTime = LocalTime.of(0, 15, 0);

        List<Restaurant> fastDeliveryRestaurants = restaurantRepository.findByAvgDeliveryTimeLessThanEqual(avgDeliveryTime);
        if (fastDeliveryRestaurants.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new CommonResponse(404, "No restaurants found with the specified delivery time", false));
        }
        return ResponseEntity.ok(new CommonResponse(200, "Fast delivery restaurants retrieved successfully", fastDeliveryRestaurants));
    }


    @Override
    @Cacheable(value = "restaurant_search", key = "#keyword")
    public ResponseEntity<CommonResponse> searchRestaurants(String keyword) {
        List<Restaurant> restaurants = restaurantRepository.searchRestaurantsByKeyword(keyword);
        List<RestaurantResponse> responses = restaurants.stream()
                .map(RestaurantResponse::convertToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new CommonResponse(200, "Restaurants retrieved successfully", responses));
    }

}