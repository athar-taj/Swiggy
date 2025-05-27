package cln.swiggy.restaurant.controller;

import cln.swiggy.restaurant.exception.ResourceNotFoundException;
import cln.swiggy.restaurant.filter.request.RestaurantFilterRequest;
import cln.swiggy.restaurant.model.Restaurant;
import cln.swiggy.restaurant.model.request.RestaurantRequest;
import cln.swiggy.restaurant.model.response.CommonResponse;
import cln.swiggy.restaurant.search.service.SearchImpl;
import cln.swiggy.restaurant.service.RestaurantService;
import cln.swiggy.restaurant.serviceImpl.otherImple.ValidationUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/restaurant")
public class RestaurantController {

    @Autowired
    RestaurantService restaurantService;
    @Autowired
    Validator validator;
    @Autowired
    ValidationUtil validationUtil;
    @Autowired
    SearchImpl searchImpl;


    @Operation(
            summary = "Create a new restaurant",
            description = "Creates a new restaurant with optional logo, multiple images, and required restaurant data. " +
                    "The restaurant data should be provided as JSON string in the restaurantData part."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Restaurant created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CommonResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data or validation failed",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CommonResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "415",
                    description = "Unsupported media type",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CommonResponse.class)
                    )
            )
    })
    @PostMapping(
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> createRestaurant(
            @Parameter(
                    description = "Restaurant logo image file (optional)",
                    content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE)
            )
            @RequestPart(value = "logo", required = false) MultipartFile logo,

            @Parameter(
                    description = "List of restaurant images (optional)",
                    content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE),
                    array = @ArraySchema(schema = @Schema(type = "string", format = "binary"))
            )
            @RequestPart(value = "images", required = false) List<MultipartFile> images,

            @Parameter(
                    description = "Restaurant details in JSON format",
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = RestaurantRequest.class)
                    )
            )
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

    @Operation(
            summary = "Get all restaurants",
            description = "Retrieves a list of all restaurants in the system"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all restaurants"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<CommonResponse> getAllRestaurants() {
        return restaurantService.getAllRestaurants();
    }

    @Operation(
            summary = "Get restaurant by ID",
            description = "Retrieves a specific restaurant by its ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurant found successfully"),
            @ApiResponse(responseCode = "404", description = "Restaurant not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{restaurantId}")
    public ResponseEntity<CommonResponse> getRestaurantById(
            @Parameter(description = "ID of the restaurant to retrieve", required = true)
            @PathVariable Long restaurantId) throws ResourceNotFoundException {
        return restaurantService.getRestaurantById(restaurantId);
    }

    @Operation(
            summary = "Update an existing restaurant",
            description = "Updates a restaurant with optional logo, multiple images, and required restaurant data. " +
                    "The restaurant data should be provided as JSON string in the restaurantData part."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurant updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data or validation failed"),
            @ApiResponse(responseCode = "404", description = "Restaurant not found"),
            @ApiResponse(responseCode = "415", description = "Unsupported media type"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping(value = "/{restaurantId}",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateRestaurant(
            @Parameter(description = "ID of the restaurant to update", required = true)
            @PathVariable Long restaurantId,

            @Parameter(
                    description = "Restaurant logo image file (optional)",
                    content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE)
            )
            @RequestPart(value = "logo", required = false) MultipartFile logo,

            @Parameter(
                    description = "List of restaurant images (optional)",
                    content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE),
                    array = @ArraySchema(schema = @Schema(type = "string", format = "binary"))
            )
            @RequestPart(value = "images", required = false) List<MultipartFile> images,

            @Parameter(
                    description = "Restaurant details in JSON format",
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = RestaurantRequest.class)
                    )
            )
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


    @Operation(
            summary = "Delete a restaurant",
            description = "Deletes a restaurant by its ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurant deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Restaurant not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{restaurantId}")
    public ResponseEntity<CommonResponse> deleteRestaurant(
            @Parameter(description = "ID of the restaurant to delete", required = true)
            @PathVariable Long restaurantId) throws ResourceNotFoundException {
        return restaurantService.deleteRestaurant(restaurantId);
    }

    @Operation(
            summary = "Get restaurant availability status",
            description = "Retrieves the current availability status of a restaurant"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved availability status"),
            @ApiResponse(responseCode = "404", description = "Restaurant not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{restaurantId}/availability")
    public ResponseEntity<CommonResponse> getRestaurantAvailability(
            @Parameter(description = "ID of the restaurant", required = true)
            @PathVariable Long restaurantId) {
        return restaurantService.getRestaurantAvailability(restaurantId);
    }

    @Operation(
            summary = "Update restaurant availability",
            description = "Updates the availability status of a restaurant"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Availability status updated successfully"),
            @ApiResponse(responseCode = "404", description = "Restaurant not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{restaurantId}/availability")
    public ResponseEntity<CommonResponse> updateRestaurantAvailability(
            @Parameter(description = "ID of the restaurant", required = true)
            @PathVariable Long restaurantId,
            @Parameter(description = "New availability status", required = true)
            @RequestParam Boolean available) {
        return restaurantService.updateRestaurantAvailability(restaurantId, available);
    }

    @Operation(
            summary = "Get restaurant outlets",
            description = "Retrieves all outlets for a given restaurant name"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved restaurant outlets"),
            @ApiResponse(responseCode = "404", description = "Restaurant not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/outlets")
    public ResponseEntity<CommonResponse> getRestaurantOutlets(
            @Parameter(description = "Name of the restaurant", required = true)
            @RequestParam("restaurantName") String restaurantName) throws ResourceNotFoundException {
        return restaurantService.getRestaurantOutlets(restaurantName);
    }

    @Operation(
            summary = "Get restaurant categories",
            description = "Retrieves all categories for a specific restaurant"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved restaurant categories"),
            @ApiResponse(responseCode = "404", description = "Restaurant not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{restaurantId}/categories")
    public ResponseEntity<CommonResponse> getRestaurantCategories(
            @Parameter(description = "ID of the restaurant", required = true)
            @PathVariable Long restaurantId) throws ResourceNotFoundException {
        return restaurantService.getRestaurantCategories(restaurantId);
    }

    @Operation(
            summary = "Get restaurant menus",
            description = "Retrieves all menus for a specific restaurant"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved restaurant menus"),
            @ApiResponse(responseCode = "404", description = "Restaurant not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{restaurantId}/menus")
    public ResponseEntity<CommonResponse> getRestaurantMenus(
            @Parameter(description = "ID of the restaurant", required = true)
            @PathVariable Long restaurantId) throws ResourceNotFoundException {
        return restaurantService.getRestaurantMenus(restaurantId);
    }

    @GetMapping("/nearest")
    public ResponseEntity<CommonResponse> getNearestRestaurant(
            @Parameter(description = "Latitude of the location", required = true)
            @RequestParam("lat") Double lat,
            @Parameter(description = "Longitude of the location", required = true)
            @RequestParam("lng") Double lng,
            @Parameter(description = "Radius of the search (in meters) (optional)")
            @RequestParam(value = "radius",required = false) Integer radius
    ){
        return restaurantService.nearestRestaurants(lat,lng,radius);
    }

    @Operation(
            summary = "Get newly Opened restaurants",
            description = "Retrieves the most recently Opened restaurants"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved new restaurants"),
            @ApiResponse(responseCode = "400", description = "Invalid limit parameter")
    })


    @GetMapping("/new")
    public ResponseEntity<CommonResponse> getNewlyRegisteredRestaurants(
            @Parameter(
                    description = "Number of restaurants to retrieve (default is 5)",
                    example = "5"
            )
            @Min(value = 1, message = "Limit must be at least 1")
            @RequestParam(required = false) Integer limit
    ) {
        return restaurantService.newlyRegisteredRestaurants(limit);
    }


    @PostMapping("/filter")
    public ResponseEntity<CommonResponse> filterRestaurants(@RequestBody RestaurantFilterRequest request) {
        return restaurantService.filterRestaurants(request);
    }

    @GetMapping("/search")
    public ResponseEntity<CommonResponse> searchRestaurants(@RequestParam("keyword") String keyword) {
        return restaurantService.searchRestaurants(keyword);
    }

    @GetMapping("/elastic-search")
    public ResponseEntity<CommonResponse> searchRestaurantsElastic(@RequestParam("keyword") String keyword) throws IOException {
        return searchImpl.searchElastic(keyword);
    }
}
