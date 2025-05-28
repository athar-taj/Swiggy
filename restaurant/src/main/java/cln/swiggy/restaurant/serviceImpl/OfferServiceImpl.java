package cln.swiggy.restaurant.serviceImpl;

import cln.swiggy.restaurant.model.Offer;
import cln.swiggy.restaurant.model.enums.OfferType;
import cln.swiggy.restaurant.model.response.CommonResponse;
import cln.swiggy.restaurant.model.response.OfferResponse;
import cln.swiggy.restaurant.repository.OfferRepository;
import cln.swiggy.restaurant.repository.RestaurantRepository;
import cln.swiggy.restaurant.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfferServiceImpl implements OfferService {

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;


    public ResponseEntity<CommonResponse> getRestaurantOffers(Long restaurantId, Boolean activeOnly,
                                                   OfferType offerType) {
        List<Offer> offers;
        if (offerType != null) {
            if (activeOnly) {
                offers = offerRepository
                        .findByRestaurantIdAndIsActiveTrueAndOfferType(restaurantId, offerType);
            } else {
                offers = offerRepository
                        .findByRestaurantIdAndOfferType(restaurantId, offerType);
            }
        } else {
            if (activeOnly) {
                offers = offerRepository
                        .findByRestaurantIdAndIsActiveTrue(restaurantId);
            } else {
                offers = offerRepository.findByRestaurantId(restaurantId);
            }
        }
        return ResponseEntity.ok(new CommonResponse(200, "Offers retrieved successfully", OfferResponse.convertToResponse(offers)));
    }
}