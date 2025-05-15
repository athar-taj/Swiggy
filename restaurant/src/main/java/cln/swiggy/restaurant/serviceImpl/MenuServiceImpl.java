package cln.swiggy.restaurant.serviceImpl;

import cln.swiggy.restaurant.exception.ResourceNotFoundException;
import cln.swiggy.restaurant.model.Menu;
import cln.swiggy.restaurant.model.MenuImage;
import cln.swiggy.restaurant.model.Restaurant;
import cln.swiggy.restaurant.model.request.MenuRequest;
import cln.swiggy.restaurant.model.response.CommonResponse;
import cln.swiggy.restaurant.model.response.MenuResponse;
import cln.swiggy.restaurant.repository.MenuImagesRepository;
import cln.swiggy.restaurant.repository.MenuRepository;
import cln.swiggy.restaurant.repository.RestaurantRepository;
import cln.swiggy.restaurant.service.MenuService;
import cln.swiggy.restaurant.serviceImpl.otherImple.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired  MenuRepository menuRepository;
    @Autowired  RestaurantRepository restaurantRepository;
    @Autowired  MenuImagesRepository menuImageRepository;
    @Autowired  ImageUtils imageUtils;

    @Override
    public ResponseEntity<CommonResponse> createMenuItem(Long restaurantId, MenuRequest request) throws ResourceNotFoundException {
            Restaurant restaurant = restaurantRepository.findById(restaurantId)
                    .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + restaurantId));

            Menu menu = new Menu();
            menu.setRestaurant(restaurant);
            menu.setName(request.getName());
            menu.setDescription(request.getDescription());
            menu.setPrice(request.getPrice());
            menu.setDiscount(request.getDiscount());
            menu.setIsAvailable(true);
            menu.setCreatedAt(LocalDateTime.now());
            menu.setUpdatedAt(LocalDateTime.now());

            Menu savedMenu = menuRepository.save(menu);

            if (request.getImages() != null && !request.getImages().isEmpty()) {
                List<String> imagePaths = imageUtils.saveImages(request.getImages(), "menu-items");
                imagePaths.forEach(path -> {
                    MenuImage image = new MenuImage();
                    image.setMenu(savedMenu);
                    image.setImage(path);
                    menuImageRepository.save(image);
                });
            }

            return ResponseEntity.ok(new CommonResponse(201, "Menu item created successfully",
                    MenuResponse.convertToResponse(menuRepository.save(savedMenu))));
    }

    @Override
    public ResponseEntity<CommonResponse> getRestaurantMenuItems(Long restaurantId) throws ResourceNotFoundException{
            List<Menu> menuItems = menuRepository.findByRestaurantId(restaurantId);

            if (menuItems.isEmpty()) {
                return ResponseEntity.ok(new CommonResponse(200, "No menu items found", new ArrayList<>()));
            }

            List<MenuResponse> responses = menuItems.stream()
                    .map(MenuResponse::convertToResponse)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(new CommonResponse(200, "Menu items retrieved successfully", responses));
    }

    @Override
    public ResponseEntity<CommonResponse> getMenuItem(Long menuItemId) throws ResourceNotFoundException {
            Menu menuItem = menuRepository.findById(menuItemId)
                    .orElseThrow(() -> new ResourceNotFoundException("Menu item not found with id: " + menuItemId));

            return ResponseEntity.ok(new CommonResponse(200, "Menu item retrieved successfully",
                    MenuResponse.convertToResponse(menuItem)));
    }

    @Override
    public ResponseEntity<CommonResponse> updateMenuItem(Long menuItemId, MenuRequest request) throws ResourceNotFoundException {
            Menu menu = menuRepository.findById(menuItemId)
                    .orElseThrow(() -> new ResourceNotFoundException("Menu item not found with id: " + menuItemId));

            menu.setName(request.getName());
            menu.setDescription(request.getDescription());
            menu.setPrice(request.getPrice());
            menu.setDiscount(request.getDiscount());
            menu.setUpdatedAt(LocalDateTime.now());

            if (request.getImages() != null && !request.getImages().isEmpty()) {
                List<MenuImage> existingImages = menuImageRepository.findByMenuId(menuItemId);
                existingImages.forEach(image -> {
                    imageUtils.deleteImage(image.getImage(), "menu-items");
                    menuImageRepository.delete(image);
                });

                List<String> imagePaths = imageUtils.saveImages(request.getImages(), "menu-items");
                imagePaths.forEach(path -> {
                    MenuImage image = new MenuImage();
                    image.setMenu(menu);
                    image.setImage(path);
                    menuImageRepository.save(image);
                });
            }

            return ResponseEntity.ok(new CommonResponse(200, "Menu item updated successfully",
                    MenuResponse.convertToResponse(menuRepository.save(menu))));
    }

    @Override
    public ResponseEntity<CommonResponse> deleteMenuItem(Long menuItemId) throws ResourceNotFoundException{
            Menu menu = menuRepository.findById(menuItemId)
                    .orElseThrow(() -> new ResourceNotFoundException("Menu item not found with id: " + menuItemId));

            List<MenuImage> images = menuImageRepository.findByMenuId(menuItemId);
            images.forEach(image -> {
                imageUtils.deleteImage(image.getImage(), "menu-items");
                menuImageRepository.delete(image);
            });

            menuRepository.delete(menu);
            return ResponseEntity.ok(new CommonResponse(200, "Menu item deleted successfully", null));
    }
}