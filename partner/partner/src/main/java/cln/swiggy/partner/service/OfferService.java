package cln.swiggy.partner.service;

import cln.swiggy.partner.model.enums.OfferType;
import cln.swiggy.partner.model.request.OfferRequest;
import cln.swiggy.partner.model.response.CommonResponse;
import org.springframework.http.ResponseEntity;

public interface OfferService {
    ResponseEntity<CommonResponse> getRestaurantOffers(Long restaurantId, Boolean activeOnly,OfferType offerType);
    ResponseEntity<CommonResponse> updateOffer(Long id, OfferRequest request);
    ResponseEntity<CommonResponse> createOffer(OfferRequest request);
}
