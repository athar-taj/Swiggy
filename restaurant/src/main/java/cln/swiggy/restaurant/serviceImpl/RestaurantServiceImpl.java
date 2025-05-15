package cln.swiggy.restaurant.serviceImpl;

import cln.swiggy.restaurant.exception.ResourceNotFoundException;
import cln.swiggy.restaurant.model.Category;
import cln.swiggy.restaurant.model.Restaurant;
import cln.swiggy.restaurant.model.RestaurantImages;
import cln.swiggy.restaurant.model.request.RestaurantRequest;
import cln.swiggy.restaurant.model.response.CommonResponse;
import cln.swiggy.restaurant.model.response.RestaurantResponse;
import cln.swiggy.restaurant.repository.CategoryRepository;
import cln.swiggy.restaurant.repository.RestaurantImagesRepository;
import cln.swiggy.restaurant.repository.RestaurantRepository;
import cln.swiggy.restaurant.service.RestaurantService;
import cln.swiggy.restaurant.serviceImpl.otherImple.ImageUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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

    @Override
    public ResponseEntity<CommonResponse> createRestaurant(RestaurantRequest request) throws ResourceNotFoundException{
            Restaurant restaurant = new Restaurant();

            // owner id
            Boolean isOwnerExists = (Boolean) rabbitTemplate.convertSendAndReceive(exchange,routingKey, request.getOwnerId());

            if (Boolean.FALSE.equals(isOwnerExists)) throw new ResourceNotFoundException("Owner not found with id: " + request.getOwnerId());
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
            restaurant.setIsAvailable(true);
            restaurant.setCreatedAt(LocalDateTime.now());
            restaurant.setUpdatedAt(LocalDateTime.now());


            if (request.getLogo() != null) {
                String logoPath = imageUtils.saveImage(request.getLogo(), "restaurant-logos");
                restaurant.setLogo(logoPath);
            }

            restaurant.getCategories().addAll(categoryRepository.findAllById(request.getCategoryIds()));

            Restaurant savedRestaurant = restaurantRepository.save(restaurant);

            if (request.getImages() != null && !request.getImages().isEmpty()) {
                List<String> imagePaths = imageUtils.saveImages(request.getImages(), "restaurant-images");
                imagePaths.forEach(path -> {
                    RestaurantImages image = new RestaurantImages();
                    image.setImage(path);
                    image.setRestaurant(savedRestaurant);
                    savedRestaurant.getImages().add(image);
                });
            }


            return ResponseEntity.ok(new CommonResponse(201, "Restaurant created successfully",
                    RestaurantResponse.convertToResponse(restaurantRepository.save(savedRestaurant))));
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

            return ResponseEntity.ok(new CommonResponse(200, "Restaurants retrieved successfully", responses));
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
            Restaurant restaurant = findRestaurantById(restaurantId);

            restaurant.setName(request.getName());
            restaurant.setEmail(request.getEmail());
            restaurant.setOutlet(request.getOutlet());
            restaurant.setContactNo(request.getContactNo());
            restaurant.setDescription(request.getDescription());
            restaurant.setRestaurantType(request.getRestaurantType());
            restaurant.setStartTime(request.getStartTime());
            restaurant.setEndTime(request.getEndTime());
            restaurant.setOpenDays(request.getOpenDays());
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

    private Restaurant findRestaurantById(Long restaurantId) {
        return restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + restaurantId));
    }

    @Override
    public ResponseEntity<CommonResponse> deleteRestaurant(Long restaurantId) throws ResourceNotFoundException{

            Restaurant restaurant = findRestaurantById(restaurantId);

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

}