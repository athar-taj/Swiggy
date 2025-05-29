package cln.swiggy.partner.serviceImpl;

import cln.swiggy.partner.exception.ResourceNotFoundException;
import cln.swiggy.partner.model.*;
import cln.swiggy.partner.model.request.RestaurantRequest;
import cln.swiggy.partner.model.response.CommonResponse;
import cln.swiggy.partner.model.response.RestaurantResponse;
import cln.swiggy.partner.repository.*;
import cln.swiggy.partner.search.model.ElasticObject;
import cln.swiggy.partner.search.repository.ElasticRepository;
import cln.swiggy.partner.service.RestaurantService;
import cln.swiggy.partner.serviceImpl.otherImple.ImageUtils;
import cln.swiggy.partner.serviceImpl.otherImple.NotificationUtil;
import com.aws.service.s3bucket.service.StorageService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    @Autowired  ElasticRepository elasticRepository;
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
    public ResponseEntity<CommonResponse> activateRestaurant(Long restaurantId){
        Optional<Restaurant> restaurants = restaurantRepository.findById(restaurantId);
        restaurants.ifPresent(restaurant -> {restaurant.setIsAvailable(true); restaurant.setUpdatedAt(LocalDateTime.now()); restaurantRepository.save(restaurant);});
        return ResponseEntity.ok(new CommonResponse(200, "Restaurant availability activated successfully", null));
    }

    @Override
    public ResponseEntity<CommonResponse> deActivateRestaurant(Long restaurantId){
        Optional<Restaurant> restaurants = restaurantRepository.findById(restaurantId);
        restaurants.ifPresent(restaurant -> {restaurant.setIsAvailable(false); restaurant.setUpdatedAt(LocalDateTime.now()); restaurantRepository.save(restaurant);});
        return ResponseEntity.ok(new CommonResponse(200, "Restaurant Deactivated successfully", null));
    }
}