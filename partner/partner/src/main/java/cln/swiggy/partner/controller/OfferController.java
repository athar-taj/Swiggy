package cln.swiggy.partner.controller;

import cln.swiggy.partner.model.enums.OfferType;
import cln.swiggy.partner.model.request.OfferRequest;
import cln.swiggy.partner.model.response.CommonResponse;
import cln.swiggy.partner.service.OfferService;
import cln.swiggy.partner.service.RestaurantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/partner/restaurants/offers")
@RequiredArgsConstructor
@Tag(name = "Offer Controller", description = "APIs for managing restaurant offers")
public class OfferController {

    @Autowired OfferService offerService;
    @Autowired RestaurantService restaurantService;

    @Operation(summary = "Get restaurant offers",
            description = "Retrieve offers for a specific restaurant with optional filters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved offers",
                    content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "404", description = "Restaurant not found"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters")
    })
    @GetMapping("/{restaurantId}")
    public ResponseEntity<CommonResponse> getRestaurantOffers(
            @Parameter(description = "ID of the restaurant", required = true)
            @PathVariable Long restaurantId,

            @Parameter(description = "Filter for active offers only")
            @RequestParam(required = false) Boolean activeOnly,

            @Parameter(description = "Filter by offer type")
            @RequestParam(required = false) OfferType offerType) {
        return offerService.getRestaurantOffers(restaurantId, activeOnly, offerType);
    }

    @Operation(summary = "Create new offer",
            description = "Create a new offer for a restaurant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Offer created successfully",
                    content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid offer data"),
            @ApiResponse(responseCode = "404", description = "Restaurant not found")
    })
    @PostMapping
    public ResponseEntity<CommonResponse> createOffer(
            @Parameter(description = "Offer details", required = true)
            @RequestBody OfferRequest request) {
        return offerService.createOffer(request);
    }

    @Operation(summary = "Update existing offer",
            description = "Update an existing offer by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Offer updated successfully",
                    content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "404", description = "Offer not found"),
            @ApiResponse(responseCode = "400", description = "Invalid offer data")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse> updateOffer(
            @Parameter(description = "ID of the offer to update", required = true)
            @PathVariable Long id,

            @Parameter(description = "Updated offer details", required = true)
            @RequestBody OfferRequest request) {
        return offerService.updateOffer(id, request);
    }
}
