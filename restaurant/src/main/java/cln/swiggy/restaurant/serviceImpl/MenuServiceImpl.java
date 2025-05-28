package cln.swiggy.restaurant.serviceImpl;

import cln.swiggy.restaurant.exception.ResourceNotFoundException;
import cln.swiggy.restaurant.filter.Implementation.MenuFilter;
import cln.swiggy.restaurant.filter.request.MenuFilterRequest;
import cln.swiggy.restaurant.model.Menu;
import cln.swiggy.restaurant.model.response.CommonResponse;
import cln.swiggy.restaurant.model.response.MenuResponse;
import cln.swiggy.restaurant.repository.MenuImagesRepository;
import cln.swiggy.restaurant.repository.MenuRepository;
import cln.swiggy.restaurant.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired  MenuRepository menuRepository;
    @Autowired  MenuImagesRepository menuImageRepository;

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