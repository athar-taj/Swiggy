package cln.swiggy.restaurant.serviceImpl;

import cln.swiggy.restaurant.exception.ResourceNotFoundException;
import cln.swiggy.restaurant.filter.Implementation.MenuFilter;
import cln.swiggy.restaurant.filter.request.MenuFilterRequest;
import cln.swiggy.restaurant.model.Category;
import cln.swiggy.restaurant.model.Menu;
import cln.swiggy.restaurant.model.MenuImage;
import cln.swiggy.restaurant.model.Restaurant;
import cln.swiggy.restaurant.model.request.MenuRequest;
import cln.swiggy.restaurant.model.response.CommonResponse;
import cln.swiggy.restaurant.model.response.MenuResponse;
import cln.swiggy.restaurant.model.response.RestaurantResponse;
import cln.swiggy.restaurant.repository.CategoryRepository;
import cln.swiggy.restaurant.repository.MenuImagesRepository;
import cln.swiggy.restaurant.repository.MenuRepository;
import cln.swiggy.restaurant.repository.RestaurantRepository;
import cln.swiggy.restaurant.search.model.ElasticObject;
import cln.swiggy.restaurant.search.repository.ElasticRepository;
import cln.swiggy.restaurant.service.MenuService;
import cln.swiggy.restaurant.serviceImpl.otherImple.ImageUtils;
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

    @Override
    public ResponseEntity<CommonResponse> createMenuItem(Long restaurantId, MenuRequest request) throws ResourceNotFoundException {
            Restaurant restaurant = restaurantRepository.findById(restaurantId)
                    .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + restaurantId));

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
        elasticObject.setMenu(MenuResponse.convertToResponse(savedMenu,menuImageRepository));
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
                .map(menu -> MenuResponse.convertToResponse(menu, menuImageRepository))
                .collect(Collectors.toList());
            return ResponseEntity.ok(new CommonResponse(200, "Menu items retrieved successfully", responses));
    }

    @Override
    public ResponseEntity<CommonResponse> getMenuItem(Long menuItemId) throws ResourceNotFoundException {
            Menu menuItem = menuRepository.findById(menuItemId)
                    .orElseThrow(() -> new ResourceNotFoundException("Menu item not found with id: " + menuItemId));

            return ResponseEntity.ok(new CommonResponse(200, "Menu item retrieved successfully",
                    MenuResponse.convertToResponse(menuItem,menuImageRepository)));
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
            menu.setCategory(category);
            menu.setDescription(request.getDescription());
            menu.setPrice(request.getPrice());
            menu.setDiscount(request.getDiscount());
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

    @Override
    public ResponseEntity<CommonResponse> filterMenu(MenuFilterRequest request) {
        Specification<Menu> specification = MenuFilter.withFilters(request);

        List<Menu> filteredRestaurant = menuRepository.findAll(specification);
        return ResponseEntity.ok(new CommonResponse(200,"Filter Menus Fetched Successfully",MenuResponse.convertToResponse(filteredRestaurant,menuImageRepository)));
    }

    @Override
    public ResponseEntity<CommonResponse> searchMenuItems(String keyword) {
        List<Menu> menus = menuRepository.searchMenuByKeyword(keyword);
        if (menus.isEmpty()) {
            return ResponseEntity.ok(new CommonResponse(200, "No menu items found", new ArrayList<>()));
        }
        List<MenuResponse> responses = menus.stream()
                .map(menu -> MenuResponse.convertToResponse(menu, menuImageRepository))
                .collect(Collectors.toList());
        return ResponseEntity.ok(new CommonResponse(200, "Menu items retrieved successfully", responses));
    }
}