package cln.swiggy.restaurant.serviceImpl;

import cln.swiggy.restaurant.exception.ResourceNotFoundException;
import cln.swiggy.restaurant.model.Restaurant;
import cln.swiggy.restaurant.model.RestaurantMenuImage;
import cln.swiggy.restaurant.model.request.RestaurantMenuImageRequest;
import cln.swiggy.restaurant.model.response.CommonResponse;
import cln.swiggy.restaurant.repository.RestaurantMenuImageRepository;
import cln.swiggy.restaurant.repository.RestaurantRepository;
import cln.swiggy.restaurant.service.RestaurantMenuImageService;
//import com.aws.service.s3bucket.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

@Service
public class RestaurantMenuImageServiceImpl implements RestaurantMenuImageService {

    @Autowired
    private RestaurantRepository restaurantRepository;

//    @Autowired
//    private StorageService storageService;

    @Autowired
    private RestaurantMenuImageRepository menuImageRepository;

    @Override
    @CacheEvict(value = "menu_images", key = "#request.restaurantId")
    public ResponseEntity<CommonResponse> addMenuImages(RestaurantMenuImageRequest request) {
        Restaurant restaurant = restaurantRepository.findById(request.getRestaurantId())
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found"));

//        for (MultipartFile image : request.getImages()) {
//            RestaurantMenuImage menuImage = new RestaurantMenuImage();
//            String key = storageService.uploadFile(image);
//            menuImage.setImage(key);
//            menuImage.setRestaurant(restaurant);
//            menuImageRepository.save(menuImage);
//        }

        return ResponseEntity.ok(new CommonResponse(
                200,
                "Menu images added successfully",
                true
        ));
    }

    @Override
    @CachePut(value = "menu_images", key = "#updatedImage.restaurant.id")
    public ResponseEntity<CommonResponse> updateMenuImage(Long imageId, MultipartFile newImage) {
        RestaurantMenuImage existingImage = menuImageRepository.findById(imageId)
                .orElseThrow(() -> new ResourceNotFoundException("Menu image not found"));

//        existingImage.setImage(storageService.uploadFile(newImage));
        RestaurantMenuImage updatedImage = menuImageRepository.save(existingImage);

        return ResponseEntity.ok(new CommonResponse(
                200,
                "Menu image updated successfully",
                updatedImage
        ));
    }

    @Override
    @Cacheable(value = "menu_images", key = "#restaurantId")
    public ResponseEntity<CommonResponse> getMenuImages(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found"));

        List<RestaurantMenuImage> images = menuImageRepository.findByRestaurant(restaurant);

        return ResponseEntity.ok(new CommonResponse(
                200,
                "Menu images retrieved successfully",
                images
        ));
    }

    @Override
    @CacheEvict(value = "menu_images", allEntries = true)
    public ResponseEntity<CommonResponse> deleteMenuImage(Long imageId) {
        RestaurantMenuImage image = menuImageRepository.findById(imageId)
                .orElseThrow(() -> new ResourceNotFoundException("Menu image not found"));

        menuImageRepository.delete(image);

        return ResponseEntity.ok(new CommonResponse(
                200,
                "Menu image deleted successfully",
                null
        ));
    }
}
