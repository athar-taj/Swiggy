package cln.swiggy.restaurant.controller;

import cln.swiggy.restaurant.model.enums.OfferType;
import cln.swiggy.restaurant.model.request.OfferRequest;
import cln.swiggy.restaurant.model.response.CommonResponse;
import cln.swiggy.restaurant.service.OfferService;
import cln.swiggy.restaurant.service.RestaurantService;
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
@RequestMapping("/api/restaurant/offers")
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

    @GetMapping("/nearby")
    public ResponseEntity<CommonResponse> findNearestRestaurantWithOffer(@RequestParam Double lat, @RequestParam Double lng, @RequestParam Integer radius) {
        return restaurantService.nearestRestaurantsWithOffers(lat, lng, radius);
    }
}
