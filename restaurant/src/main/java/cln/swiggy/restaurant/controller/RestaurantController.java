package cln.swiggy.restaurant.controller;

import cln.swiggy.restaurant.exception.ResourceNotFoundException;
import cln.swiggy.restaurant.model.request.RestaurantRequest;
import cln.swiggy.restaurant.model.response.CommonResponse;
import cln.swiggy.restaurant.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CommonResponse> createRestaurant(
            @ModelAttribute RestaurantRequest request) throws ResourceNotFoundException {
        return restaurantService.createRestaurant(request);
    }

    @GetMapping
    public ResponseEntity<CommonResponse> getAllRestaurants() {
        return restaurantService.getAllRestaurants();
    }

    @GetMapping("/{restaurantId}")
    public ResponseEntity<CommonResponse> getRestaurantById(
            @PathVariable Long restaurantId) throws ResourceNotFoundException {
        return restaurantService.getRestaurantById(restaurantId);
    }

    @PutMapping(value = "/{restaurantId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CommonResponse> updateRestaurant(
            @PathVariable Long restaurantId,
            @ModelAttribute RestaurantRequest request) throws ResourceNotFoundException {
        return restaurantService.updateRestaurant(restaurantId, request);
    }

    @DeleteMapping("/{restaurantId}")
    public ResponseEntity<CommonResponse> deleteRestaurant(
            @PathVariable Long restaurantId) throws ResourceNotFoundException {
        return restaurantService.deleteRestaurant(restaurantId);
    }

    @GetMapping("/{restaurantId}/availability")
    public ResponseEntity<CommonResponse> getRestaurantAvailability(
            @PathVariable Long restaurantId) {
        return restaurantService.getRestaurantAvailability(restaurantId);
    }

    @PutMapping("/{restaurantId}/availability")
    public ResponseEntity<CommonResponse> updateRestaurantAvailability(
            @PathVariable Long restaurantId,
            @RequestParam Boolean available) {
        return restaurantService.updateRestaurantAvailability(restaurantId, available);
    }

    @GetMapping("/outlets")
    public ResponseEntity<CommonResponse> getRestaurantOutlets(@RequestParam("restaurantName") String restaurantName) throws ResourceNotFoundException {
        return restaurantService.getRestaurantOutlets(restaurantName);
    }

    @GetMapping("/{restaurantId}/categories")
    public ResponseEntity<CommonResponse> getRestaurantCategories(
            @PathVariable Long restaurantId) throws ResourceNotFoundException {
        return restaurantService.getRestaurantCategories(restaurantId);
    }
}