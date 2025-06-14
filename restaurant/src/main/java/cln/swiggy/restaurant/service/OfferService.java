package cln.swiggy.restaurant.service;

import cln.swiggy.restaurant.model.enums.OfferType;
import cln.swiggy.restaurant.model.response.CommonResponse;
import org.springframework.http.ResponseEntity;

public interface OfferService {
    ResponseEntity<CommonResponse> getRestaurantOffers(Long restaurantId, Boolean activeOnly, OfferType offerType);
}
