package cln.swiggy.restaurant.controller;

import cln.swiggy.restaurant.exception.ResourceNotFoundException;
import cln.swiggy.restaurant.model.request.RestaurantRequest;
import cln.swiggy.restaurant.model.response.CommonResponse;
import cln.swiggy.restaurant.service.RestaurantService;
import cln.swiggy.restaurant.serviceImpl.otherImple.ValidationUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/restaurant")
public class RestaurantController {

    @Autowired
    RestaurantService restaurantService;
    @Autowired
    Validator validator;
    @Autowired
    ValidationUtil validationUtil;

    @PostMapping(
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> createRestaurant(
            @RequestPart(value = "logo", required = false) MultipartFile logo,
            @RequestPart(value = "images", required = false) List<MultipartFile> images,
            @RequestPart(value = "restaurantData", required = true) String restaurantDataJson
    ) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        RestaurantRequest request = mapper.readValue(restaurantDataJson, RestaurantRequest.class);
        request.setLogo(logo);
        request.setImages(images);

        validationUtil.validateRequest(request);

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
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateRestaurant(
            @PathVariable Long restaurantId,
            @RequestPart(value = "logo", required = false) MultipartFile logo,
            @RequestPart(value = "images", required = false) List<MultipartFile> images,
            @RequestPart(value = "restaurantData", required = true) String restaurantDataJson
    ) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        RestaurantRequest request = mapper.readValue(restaurantDataJson, RestaurantRequest.class);
        request.setLogo(logo);
        request.setImages(images);

        validationUtil.validateRequest(request);
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

    @GetMapping("/{restaurantId}/menus")
    public ResponseEntity<CommonResponse> getRestaurantMenus(
            @PathVariable Long restaurantId) throws ResourceNotFoundException {
        return restaurantService.getRestaurantMenus(restaurantId);
    }

}