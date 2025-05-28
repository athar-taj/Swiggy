package cln.swiggy.restaurant.controller;

import cln.swiggy.restaurant.model.response.CommonResponse;
import cln.swiggy.restaurant.service.FacilityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/facilities")
@Tag(name = "Restaurant Facilities", description = "APIs for managing restaurant facilities and amenities")
public class FacilityController {

    @Autowired
    private FacilityService facilityService;

    @Operation(summary = "Get restaurant facilities",
            description = "Retrieves all facilities associated with a specific restaurant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Facilities retrieved successfully",
                    content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "404", description = "Restaurant not found")
    })
    @GetMapping("/restaurants/{restaurantId}")
    public ResponseEntity<CommonResponse> getFacilitiesByRestaurant(
            @Parameter(description = "ID of the restaurant", required = true)
            @PathVariable Long restaurantId) {
        return facilityService.getFacilitiesByRestaurantId(restaurantId);
    }
}