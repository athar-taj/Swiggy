package cln.swiggy.restaurant.controller;

import cln.swiggy.restaurant.exception.ResourceNotFoundException;
import cln.swiggy.restaurant.model.request.MenuRequest;
import cln.swiggy.restaurant.model.response.CommonResponse;
import cln.swiggy.restaurant.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/menus")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @PostMapping(value = "/restaurants/{restaurantId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CommonResponse> createMenuItem(
            @PathVariable Long restaurantId,
            @ModelAttribute MenuRequest request) throws ResourceNotFoundException {
        return menuService.createMenuItem(restaurantId, request);
    }

    @GetMapping("/{menuItemId}")
    public ResponseEntity<CommonResponse> getMenuItem(
            @PathVariable Long menuItemId) throws ResourceNotFoundException {
        return menuService.getMenuItem(menuItemId);
    }

    @GetMapping("/restaurants/{restaurantId}")
    public ResponseEntity<CommonResponse> getRestaurantMenuItems(
            @PathVariable Long restaurantId) throws ResourceNotFoundException {
        return menuService.getRestaurantMenuItems(restaurantId);
    }

    @PutMapping(value = "/{menuItemId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CommonResponse> updateMenuItem(
            @PathVariable Long menuItemId,
            @ModelAttribute MenuRequest request) throws ResourceNotFoundException {
        return menuService.updateMenuItem(menuItemId, request);
    }

    @DeleteMapping("/{menuItemId}")
    public ResponseEntity<CommonResponse> deleteMenuItem(
            @PathVariable Long menuItemId) throws ResourceNotFoundException {
        return menuService.deleteMenuItem(menuItemId);
    }
}
