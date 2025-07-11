
package cln.swiggy.partner.controller;

import cln.swiggy.partner.exception.ResourceNotFoundException;
import cln.swiggy.partner.model.request.MenuRequest;
import cln.swiggy.partner.model.response.CommonResponse;
import cln.swiggy.partner.service.MenuService;
import cln.swiggy.partner.serviceImpl.otherImple.ValidationUtil;
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

import java.util.List;

@RestController
@RequestMapping("/api/partner/menu")
@Tag(name = "Restaurant Menu", description = "APIs for managing restaurant menu items")
public class MenuController {

    @Autowired
    private MenuService menuService;
    @Autowired
    private ValidationUtil validationUtil;

    @Operation(summary = "Create menu item",
            description = "Creates a new menu item for a restaurant with optional multiple images")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Menu item created successfully",
                    content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Restaurant not found"),
            @ApiResponse(responseCode = "415", description = "Unsupported media type")
    })
    @PostMapping(value = "/restaurant",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse> createMenuItem(
            @Parameter(description = "Menu item images (optional)")
            @RequestPart(value = "image", required = false) List<MultipartFile> image,
            @Parameter(description = "Menu item details in JSON format", required = true)
            @RequestPart(value = "menuData", required = true) String menuDataJson) throws ResourceNotFoundException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        MenuRequest request = mapper.readValue(menuDataJson, MenuRequest.class);
        request.setImages(image);
        validationUtil.validateRequest(request);
        return menuService.createMenuItem(request);
    }

    @Operation(summary = "Get menu item",
            description = "Retrieves details of a specific menu item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Menu item retrieved successfully",
                    content = @Content(schema = @Schema(implementation = CommonResponse.class))),
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
            @ApiResponse(responseCode = "200", description = "Menu items retrieved successfully",
                    content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "404", description = "Restaurant not found")
    })
    @GetMapping("/restaurants/{restaurantId}")
    public ResponseEntity<CommonResponse> getRestaurantMenuItems(
            @Parameter(description = "ID of the restaurant", required = true)
            @PathVariable Long restaurantId) throws ResourceNotFoundException {
        return menuService.getRestaurantMenuItems(restaurantId);
    }

    @Operation(summary = "Update menu item",
            description = "Updates an existing menu item with optional image updates")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Menu item updated successfully",
                    content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Menu item not found"),
            @ApiResponse(responseCode = "415", description = "Unsupported media type")
    })
    @PutMapping(value = "/{menuItemId}",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse> updateMenuItem(
            @Parameter(description = "ID of the menu item to update", required = true)
            @PathVariable Long menuItemId,
            @Parameter(description = "Updated menu item images (optional)")
            @RequestPart(value = "image", required = false) List<MultipartFile> image,
            @Parameter(description = "Updated menu item details in JSON format", required = true)
            @RequestPart(value = "menuData", required = true) String menuDataJson) throws ResourceNotFoundException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        MenuRequest request = mapper.readValue(menuDataJson, MenuRequest.class);
        request.setImages(image);
        validationUtil.validateRequest(request);
        return menuService.updateMenuItem(menuItemId, request);
    }

    @Operation(summary = "Delete menu item",
            description = "Removes a menu item from the restaurant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Menu item deleted successfully",
                    content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "404", description = "Menu item not found")
    })
    @DeleteMapping("/{menuItemId}")
    public ResponseEntity<CommonResponse> deleteMenuItem(
            @Parameter(description = "ID of the menu item to delete", required = true)
            @PathVariable Long menuItemId) throws ResourceNotFoundException {
        return menuService.deleteMenuItem(menuItemId);
    }
}