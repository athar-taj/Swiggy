
package cln.swiggy.restaurant.controller;

import cln.swiggy.restaurant.exception.ResourceNotFoundException;
import cln.swiggy.restaurant.filter.Implementation.MenuRecommendation;
import cln.swiggy.restaurant.filter.request.MenuFilterRequest;
import cln.swiggy.restaurant.model.request.MenuRequest;
import cln.swiggy.restaurant.model.response.CommonResponse;
import cln.swiggy.restaurant.service.MenuService;
import cln.swiggy.restaurant.serviceImpl.otherImple.ValidationUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.CookieManager;
import java.util.List;

@RestController
@RequestMapping("/api/menu")
@Tag(name = "Restaurant Menu", description = "APIs for managing restaurant menu items")
public class MenuController {

    @Autowired
    private MenuService menuService;
    @Autowired
    private ValidationUtil validationUtil;
    @Autowired
    MenuRecommendation menuRecommendation;


    @Operation(summary = "Get menu item",
            description = "Retrieves details of a specific menu item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Menu item retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Menu item not found")
    })
    @GetMapping("/{menuItemId}")
    public ResponseEntity<CommonResponse> getMenuItem(
            @Parameter(description = "ID of the menu item", required = true)
            @PathVariable Long menuItemId) throws ResourceNotFoundException {
        return menuService.getMenuItem(menuItemId);
    }

    @Operation(summary = "Get restaurant menu",
            description = "Retrieves all menu items for a specific restaurant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Menu items retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Restaurant not found")
    })
    @GetMapping("/restaurants/{restaurantId}")
    public ResponseEntity<CommonResponse> getRestaurantMenuItems(
            @Parameter(description = "ID of the restaurant", required = true)
            @PathVariable Long restaurantId) throws ResourceNotFoundException {
        return menuService.getRestaurantMenuItems(restaurantId);
    }

    @Operation(summary = "Filter menus based on criteria",
            description = "Applies filters to menu items and retrieves the results")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Filtered menu items retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid filter criteria")
    })
    @PostMapping("/filter")
    public ResponseEntity<CommonResponse> filterMenu(@RequestBody MenuFilterRequest request) {
        return menuService.filterMenu(request);
    }

    @Operation(summary = "Search menu items by keyword",
            description = "Searches menu items using a specific keyword")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search results retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No menu items found for the given keyword")
    })
    @GetMapping("/search")
    public ResponseEntity<CommonResponse> searchMenus(@RequestParam("keyword") String keyword) {
        return menuService.searchMenuItems(keyword);
    }

    @Operation(summary = "Recommend menus by most orders",
            description = "Recommends popular menu items based on the number of orders")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recommendations retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid limit value")
    })
    @GetMapping("/recommend/by-order")
    public ResponseEntity<CommonResponse> searchMenus(@RequestParam("limit") int limit) {
        return menuRecommendation.recommendMenuByMostOrders(limit);
    }

    @Operation(summary = "Recommend menus for a user",
            description = "Recommends menu items for a specific user based on their order history")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User-specific recommendations retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/recommend/user-orders")
    public ResponseEntity<CommonResponse> recommendMenuByUsersOrder(@RequestParam("userId") Long userId) {
        return menuRecommendation.recommendMenuByUserOrders(userId);
    }

    @Operation(summary = "Recommend fast delivery menu items",
            description = "Recommends menu items that can be delivered quickly to a given location")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fast delivery recommendations retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid location coordinates")
    })
    @GetMapping("/recommend/fast-delivery")
    public ResponseEntity<CommonResponse> recommendMenuByFastDelivery(@RequestParam("lat") Double lat, @RequestParam("lng") Double lng) {
        return menuRecommendation.recommendByFastDelivery(lat, lng);
    }


}