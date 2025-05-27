package cln.swiggy.restaurant.serviceImpl;

import cln.swiggy.restaurant.exception.ResourceNotFoundException;
import cln.swiggy.restaurant.filter.Implementation.RestaurantFilter;
import cln.swiggy.restaurant.filter.request.RestaurantFilterRequest;
import cln.swiggy.restaurant.model.*;
import cln.swiggy.restaurant.model.request.RestaurantRequest;
import cln.swiggy.restaurant.model.response.CommonResponse;
import cln.swiggy.restaurant.model.response.RestaurantResponse;
import cln.swiggy.restaurant.repository.*;
import cln.swiggy.restaurant.search.model.ElasticObject;
import cln.swiggy.restaurant.search.repository.ElasticRepository;
import cln.swiggy.restaurant.service.RestaurantService;
import cln.swiggy.restaurant.serviceImpl.otherImple.CalculateDistance;
import cln.swiggy.restaurant.serviceImpl.otherImple.ImageUtils;
import com.aws.service.s3bucket.service.StorageService;
import lombok.Getter;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.routing-key}")
    private String routingKey;

    @Autowired  RestaurantRepository restaurantRepository;
    @Autowired  CategoryRepository categoryRepository;
    @Autowired  ImageUtils imageUtils;
    @Autowired  RabbitTemplate rabbitTemplate;
    @Autowired  RestaurantImagesRepository restaurantImagesRepository;
    @Autowired  StorageService storageService;
    @Autowired  AddressRepository addressRepository;
    @Autowired  ElasticRepository elasticRepository;
    @Autowired  OfferRepository offerRepository;

    @Override
    public ResponseEntity<CommonResponse> createRestaurant(RestaurantRequest request) throws ResourceNotFoundException{

            if (restaurantRepository.existsByNameAndOutlet(request.getName(), request.getOutlet())) {
                return ResponseEntity.badRequest()
                        .body(new CommonResponse(400, "Restaurant with same name and outlet already exists", null));
            }

            Restaurant restaurant = new Restaurant();

            Boolean isOwnerExists = (Boolean) rabbitTemplate.convertSendAndReceive(exchange, routingKey, request.getOwnerId());

            if (Boolean.FALSE.equals(isOwnerExists)) {
                throw new ResourceNotFoundException("Owner not found with id: " + request.getOwnerId());
            }

            restaurant.setOwnerId(request.getOwnerId());
            restaurant.setName(request.getName());
            restaurant.setEmail(request.getEmail());
            restaurant.setOutlet(request.getOutlet());
            restaurant.setContactNo(request.getContactNo());
            restaurant.setDescription(request.getDescription());
            restaurant.setRestaurantType(request.getRestaurantType());
            restaurant.setStartTime(request.getStartTime());
            restaurant.setEndTime(request.getEndTime());
            restaurant.setOpenDays(request.getOpenDays());
            restaurant.setAvgDeliveryTime(request.getAvgDeliveryTime());
            restaurant.setCostForTwo(request.getCostForTwo());
            restaurant.setIsAvailable(true);
            restaurant.setCreatedAt(LocalDateTime.now());
            restaurant.setUpdatedAt(LocalDateTime.now());


            if (request.getLogo() != null) {
//                restaurant.setLogo(storageService.uploadFile(request.getLogo()));
                restaurant.setLogo("logo.file");
            }

            restaurant.getCategories().addAll(categoryRepository.findAllById(request.getCategoryIds()));

            Restaurant savedRestaurant = restaurantRepository.save(restaurant);

            if (request.getImages() != null && !request.getImages().isEmpty()) {
                for (MultipartFile file : request.getImages()) {
//                    String key = storageService.uploadFile(file);
                    RestaurantImages restaurantImages = new RestaurantImages();
                    restaurantImages.setImage("Image.pic");
                    restaurantImages.setRestaurant(savedRestaurant);
                    restaurantImagesRepository.save(restaurantImages);
                }
            }

            ElasticObject elasticObject = new ElasticObject();
            elasticObject.setElementId(savedRestaurant.getId());
            elasticObject.setLabel("Restaurant");
            elasticObject.setRestaurant(RestaurantResponse.convertToResponse(savedRestaurant));
            elasticRepository.save(elasticObject);

            return ResponseEntity.ok(new CommonResponse(201, "Restaurant created successfully",
                    true));
    }

    @Override
    public ResponseEntity<CommonResponse> getAllRestaurants() {
            List<Restaurant> restaurants = restaurantRepository.findAll();

            if (restaurants.isEmpty()) {
                return ResponseEntity.ok(new CommonResponse(200, "No restaurants found", new ArrayList<>()));
            }

            List<RestaurantResponse> responses = restaurants.stream()
                    .map(RestaurantResponse::convertToResponse)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(new CommonResponse(200, "ElasticObject retrieved successfully", responses));
    }

    @Override
    public ResponseEntity<CommonResponse> getRestaurantById(Long restaurantId) throws ResourceNotFoundException{
            Restaurant restaurant = restaurantRepository.findById(restaurantId)
                    .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + restaurantId));

            return ResponseEntity.ok(new CommonResponse(200, "Restaurant retrieved successfully",
                    RestaurantResponse.convertToResponse(restaurant)));
    }

    @Override
    public ResponseEntity<CommonResponse> updateRestaurant(Long restaurantId, RestaurantRequest request) throws ResourceNotFoundException {
            Restaurant restaurant = restaurantRepository.findById(restaurantId)
                    .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + restaurantId));

            restaurant.setName(request.getName());
            restaurant.setEmail(request.getEmail());
            restaurant.setOutlet(request.getOutlet());
            restaurant.setContactNo(request.getContactNo());
            restaurant.setDescription(request.getDescription());
            restaurant.setRestaurantType(request.getRestaurantType());
            restaurant.setStartTime(request.getStartTime());
            restaurant.setEndTime(request.getEndTime());
            restaurant.setOpenDays(request.getOpenDays());
            restaurant.setAvgDeliveryTime(request.getAvgDeliveryTime());
            restaurant.setCostForTwo(request.getCostForTwo());
            restaurant.setUpdatedAt(LocalDateTime.now());

            if (request.getLogo() != null) {
                String newLogoPath = imageUtils.updateImage(restaurant.getLogo(), request.getLogo(), "restaurant-logos");
                restaurant.setLogo(newLogoPath);
            }

            restaurant.getCategories().clear();
            restaurant.getCategories().addAll(categoryRepository.findAllById(request.getCategoryIds()));

            if (request.getImages() != null && !request.getImages().isEmpty()) {
                restaurant.getImages().forEach(image ->
                        imageUtils.deleteImage(image.getImage(), "restaurant-images"));
                restaurant.getImages().clear();

                List<String> imagePaths = imageUtils.saveImages(request.getImages(), "restaurant-images");
                imagePaths.forEach(path -> {
                    RestaurantImages image = new RestaurantImages();
                    image.setImage(path);
                    image.setRestaurant(restaurant);
                    restaurant.getImages().add(image);
                });
            }

            return ResponseEntity.ok(new CommonResponse(200, "Restaurant updated successfully",
                    RestaurantResponse.convertToResponse(restaurantRepository.save(restaurant))));
    }

    @Override
    public ResponseEntity<CommonResponse> deleteRestaurant(Long restaurantId) throws ResourceNotFoundException{

            Restaurant restaurant = restaurantRepository.findById(restaurantId)
                    .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + restaurantId));

            if (restaurant.getLogo() != null) {
                imageUtils.deleteImage(restaurant.getLogo(), "restaurant-logos");
            }

            restaurant.getImages().forEach(image ->
                    imageUtils.deleteImage(image.getImage(), "restaurant-images"));

            restaurantRepository.delete(restaurant);
            return ResponseEntity.ok(new CommonResponse(200, "Restaurant deleted successfully", null));
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
        List<Restaurant> restaurants = restaurantRepository.findAllByName(restaurantName);
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
    public ResponseEntity<CommonResponse> getRestaurantCategories(Long restaurantId) throws ResourceNotFoundException {
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
    public ResponseEntity<CommonResponse> searchRestaurants(String keyword) {
        List<Restaurant> restaurants = restaurantRepository.searchRestaurantsByKeyword(keyword);
        if (restaurants.isEmpty()) {
            return ResponseEntity.ok(new CommonResponse(200, "No restaurants found", new ArrayList<>()));
        }
        List<RestaurantResponse> responses = restaurants.stream()
                .map(RestaurantResponse::convertToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new CommonResponse(200, "Restaurants retrieved successfully", responses));
    }

}