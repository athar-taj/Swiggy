package cln.swiggy.partner.controller;

import cln.swiggy.partner.model.request.FacilityRequest;
import cln.swiggy.partner.model.response.CommonResponse;
import cln.swiggy.partner.service.FacilityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/facilities")
@Tag(name = "Restaurant Facilities", description = "APIs for managing restaurant facilities and amenities")
public class FacilityController {

    @Autowired
    private FacilityService facilityService;

    @Operation(summary = "Add new facility",
            description = "Creates a new facility that can be associated with restaurants")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Facility added successfully",
                    content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid facility data")
    })
    @PostMapping
    public ResponseEntity<CommonResponse> addFacility(
            @Parameter(description = "Facility details", required = true)
            @Valid @RequestBody FacilityRequest request) {
        return facilityService.addFacility(request);
    }

    @Operation(summary = "Update facility",
            description = "Updates an existing facility's details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Facility updated successfully",
                    content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid facility data"),
            @ApiResponse(responseCode = "404", description = "Facility not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse> updateFacility(
            @Parameter(description = "ID of the facility to update", required = true)
            @PathVariable Long id,
            @Parameter(description = "Updated facility details", required = true)
            @Valid @RequestBody FacilityRequest request) {
        return facilityService.updateFacility(id, request);
    }

    @Operation(summary = "Delete facility",
            description = "Removes a facility from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Facility deleted successfully",
                    content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "404", description = "Facility not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse> deleteFacility(
            @Parameter(description = "ID of the facility to delete", required = true)
            @PathVariable Long id) {
        return facilityService.deleteFacility(id);
    }

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