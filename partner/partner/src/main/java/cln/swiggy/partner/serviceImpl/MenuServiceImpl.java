package cln.swiggy.partner.serviceImpl;

import cln.swiggy.partner.exception.ResourceNotFoundException;
import cln.swiggy.partner.model.Category;
import cln.swiggy.partner.model.Menu;
import cln.swiggy.partner.model.MenuImage;
import cln.swiggy.partner.model.Restaurant;
import cln.swiggy.partner.model.request.MenuRequest;
import cln.swiggy.partner.model.response.CommonResponse;
import cln.swiggy.partner.model.response.MenuResponse;
import cln.swiggy.partner.repository.CategoryRepository;
import cln.swiggy.partner.repository.MenuImagesRepository;
import cln.swiggy.partner.repository.MenuRepository;
import cln.swiggy.partner.repository.RestaurantRepository;
import cln.swiggy.partner.search.model.ElasticObject;
import cln.swiggy.partner.search.repository.ElasticRepository;
import cln.swiggy.partner.service.MenuService;
import cln.swiggy.partner.serviceImpl.otherImple.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired  MenuRepository menuRepository;
    @Autowired  RestaurantRepository restaurantRepository;
    @Autowired  MenuImagesRepository menuImageRepository;
    @Autowired  CategoryRepository categoryRepository;
    @Autowired  ImageUtils imageUtils;
    @Autowired  ElasticRepository elasticRepository;
    @Autowired  MenuResponse menuResponse;

    @Override
    public ResponseEntity<CommonResponse> createMenuItem(MenuRequest request) throws ResourceNotFoundException {
            Restaurant restaurant = restaurantRepository.findById(request.getRestaurantId())
                    .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + request.getRestaurantId()));

            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + request.getCategoryId()));

            if (menuRepository.existsByNameAndRestaurantAndPrice(request.getName(), restaurant, request.getPrice())) {
                return ResponseEntity.badRequest()
                        .body(new CommonResponse(400, "Menu item with same name and price already exists in this restaurant", false));
            }


            Menu menu = new Menu();
            menu.setRestaurant(restaurant);
            menu.setCategory(category);
            menu.setName(request.getName());
            menu.setDescription(request.getDescription());
            menu.setPrice(request.getPrice());
            menu.setDiscount(request.getDiscount());
            menu.setMinimumOrderValue(request.getMinimumOrderValue());
            menu.setMenuType(request.getMenuType());
            menu.setIsAvailable(true);
            menu.setCreatedAt(LocalDateTime.now());
            menu.setUpdatedAt(LocalDateTime.now());

            Menu savedMenu = menuRepository.save(menu);

        if (request.getImages() != null && !request.getImages().isEmpty()) {
            for (MultipartFile file : request.getImages()) {
//              String key = storageService.uploadFile(file);
                MenuImage image = new MenuImage();
                image.setMenu(savedMenu);
                image.setImage("menu.jpg");
                menuImageRepository.save(image);
            }
        }

        ElasticObject elasticObject = new ElasticObject();
        elasticObject.setElementId(savedMenu.getId());
        elasticObject.setLabel("Dish");
        elasticObject.setMenu(menuResponse.convertToResponse(savedMenu));
        elasticRepository.save(elasticObject);

        return ResponseEntity.ok(new CommonResponse(201, "Menu item created successfully",true));
    }

    @Override
    public ResponseEntity<CommonResponse> getRestaurantMenuItems(Long restaurantId) throws ResourceNotFoundException{
            List<Menu> menuItems = menuRepository.findByRestaurantId(restaurantId);

            if (menuItems.isEmpty()) {
                return ResponseEntity.ok(new CommonResponse(200, "No menu items found", new ArrayList<>()));
            }

        List<MenuResponse> responses = menuItems.stream()
                .map(menu -> menuResponse.convertToResponse(menu))
                .collect(Collectors.toList());
            return ResponseEntity.ok(new CommonResponse(200, "Menu items retrieved successfully", responses));
    }

    @Override
    public ResponseEntity<CommonResponse> getMenuItem(Long menuItemId) throws ResourceNotFoundException {
            Menu menuItem = menuRepository.findById(menuItemId)
                    .orElseThrow(() -> new ResourceNotFoundException("Menu item not found with id: " + menuItemId));

            return ResponseEntity.ok(new CommonResponse(200, "Menu item retrieved successfully",
                    menuResponse.convertToResponse(menuItem)));
    }

    @Override
    public ResponseEntity<CommonResponse> updateMenuItem(Long menuItemId, MenuRequest request) throws ResourceNotFoundException {
            Menu menu = menuRepository.findById(menuItemId)
                    .orElseThrow(() -> new ResourceNotFoundException("Menu item not found with id: " + menuItemId));

            Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + request.getCategoryId()));

            if (menuRepository.existsByNameAndRestaurantAndPrice(request.getName(), menu.getRestaurant(), request.getPrice())) {
                return ResponseEntity.badRequest()
                        .body(new CommonResponse(400, "Menu item with same name and price already exists in this restaurant", false));
            }

            menu.setName(request.getName());
            menu.setMenuType(request.getMenuType());
            menu.setCategory(category);
            menu.setDescription(request.getDescription());
            menu.setPrice(request.getPrice());
            menu.setDiscount(request.getDiscount());
            menu.setMinimumOrderValue(request.getMinimumOrderValue());
            menu.setUpdatedAt(LocalDateTime.now());

            if (request.getImages() != null && !request.getImages().isEmpty()) {
                List<MenuImage> existingImages = menuImageRepository.findByMenu(menu);
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

            return ResponseEntity.ok(new CommonResponse(200, "Menu item updated successfully",true));
    }

    @Override
    public ResponseEntity<CommonResponse> deleteMenuItem(Long menuItemId) throws ResourceNotFoundException{
            Menu menu = menuRepository.findById(menuItemId)
                    .orElseThrow(() -> new ResourceNotFoundException("Menu item not found with id: " + menuItemId));

            List<MenuImage> images = menuImageRepository.findByMenu(menu);
            images.forEach(image -> {
                imageUtils.deleteImage(image.getImage(), "menu-items");
                menuImageRepository.delete(image);
            });

            menuRepository.delete(menu);
            return ResponseEntity.ok(new CommonResponse(200, "Menu item deleted successfully", null));
    }
}