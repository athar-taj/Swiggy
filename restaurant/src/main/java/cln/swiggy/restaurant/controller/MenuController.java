package cln.swiggy.restaurant.controller;

import cln.swiggy.restaurant.exception.ResourceNotFoundException;
import cln.swiggy.restaurant.model.request.MenuRequest;
import cln.swiggy.restaurant.model.response.CommonResponse;
import cln.swiggy.restaurant.service.MenuService;
import cln.swiggy.restaurant.serviceImpl.otherImple.ValidationUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;
    @Autowired
    private ValidationUtil validationUtil;

    @PostMapping(value = "/restaurant/{restaurantId}",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse> createMenuItem(
            @PathVariable Long restaurantId,
            @RequestPart(value = "image", required = false) List<MultipartFile> image,
            @RequestPart(value = "menuData", required = true) String menuDataJson) throws ResourceNotFoundException, JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        MenuRequest request = mapper.readValue(menuDataJson, MenuRequest.class);
        request.setImages(image);

        validationUtil.validateRequest(request);

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
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse> updateMenuItem(
            @PathVariable Long menuItemId,
            @RequestPart(value = "image", required = false) List<MultipartFile> image,
            @RequestPart(value = "menuData", required = true) String menuDataJson) throws ResourceNotFoundException, JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        MenuRequest request = mapper.readValue(menuDataJson, MenuRequest.class);
        request.setImages(image);

        validationUtil.validateRequest(request);

        return menuService.updateMenuItem(menuItemId, request);
    }

    @DeleteMapping("/{menuItemId}")
    public ResponseEntity<CommonResponse> deleteMenuItem(
            @PathVariable Long menuItemId) throws ResourceNotFoundException {
        return menuService.deleteMenuItem(menuItemId);
    }
}
