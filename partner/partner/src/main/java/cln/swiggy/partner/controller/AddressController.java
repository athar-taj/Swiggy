package cln.swiggy.partner.controller;

import cln.swiggy.partner.model.request.AddressRequest;
import cln.swiggy.partner.model.response.CommonResponse;
import cln.swiggy.partner.service.AddressService;
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
@RequestMapping("/api/partner/restaurants")
@Tag(name = "Restaurant Address", description = "Restaurant Address Management APIs")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @Operation(summary = "Create a new address for a restaurant",
            description = "Creates a new address entry for the specified restaurant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Address created successfully",
                    content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Restaurant not found")
    })
    @PostMapping("/address")
    public ResponseEntity<CommonResponse> createAddress(
            @Parameter(description = "Address details", required = true)
            @Valid @RequestBody AddressRequest request) {
        return addressService.createAddress(request);
    }

    @Operation(summary = "Get restaurant address",
            description = "Retrieves the address details for the specified restaurant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Address retrieved successfully",
                    content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "404", description = "Restaurant or address not found")
    })
    @GetMapping("/{restaurantId}/address")
    public ResponseEntity<CommonResponse> getAddress(
            @Parameter(description = "ID of the restaurant", required = true)
            @PathVariable Long restaurantId) {
        return addressService.getAddress(restaurantId);
    }

    @Operation(summary = "Update restaurant address",
            description = "Updates the existing address for the specified restaurant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Address updated successfully",
                    content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Restaurant or address not found")
    })
    @PutMapping("/{restaurantId}/address")
    public ResponseEntity<CommonResponse> updateAddress(
            @Parameter(description = "ID of the restaurant", required = true)
            @PathVariable Long restaurantId,
            @Parameter(description = "Updated address details", required = true)
            @Valid @RequestBody AddressRequest request) {
        return addressService.updateAddress(restaurantId, request);
    }

    @Operation(summary = "Delete restaurant address",
            description = "Deletes the address for the specified restaurant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Address deleted successfully",
                    content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "404", description = "Restaurant or address not found")
    })
    @DeleteMapping("/{restaurantId}/address")
    public ResponseEntity<CommonResponse> deleteAddress(
            @Parameter(description = "ID of the restaurant", required = true)
            @PathVariable Long restaurantId) {
        return addressService.deleteAddress(restaurantId);
    }

    @Operation(summary = "Get restaurant outlets",
            description = "Retrieve all outlet addresses associated with a specific restaurant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved restaurant outlets"),
            @ApiResponse(responseCode = "404", description = "Restaurant not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{restaurantId}/outlets")
    public ResponseEntity<CommonResponse> getRestaurantOutlets(
            @Parameter(description = "ID of the restaurant to get outlets for", required = true)
            @PathVariable Long restaurantId) {
        return addressService.getRestaurantOutlets(restaurantId);
    }

}

