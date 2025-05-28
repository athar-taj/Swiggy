package cln.swiggy.partner.serviceImpl;

import cln.swiggy.partner.exception.ResourceNotFoundException;
import cln.swiggy.partner.model.Offer;
import cln.swiggy.partner.model.Restaurant;
import cln.swiggy.partner.model.enums.OfferType;
import cln.swiggy.partner.model.request.OfferRequest;
import cln.swiggy.partner.model.response.CommonResponse;
import cln.swiggy.partner.model.response.OfferResponse;
import cln.swiggy.partner.repository.OfferRepository;
import cln.swiggy.partner.repository.RestaurantRepository;
import cln.swiggy.partner.service.OfferService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OfferServiceImpl implements OfferService {

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;


    public ResponseEntity<CommonResponse> createOffer(OfferRequest request) {
        Restaurant restaurant = restaurantRepository.findById(request.getRestaurantId())
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found"));

        Offer offer = new Offer();

        offer.setOfferName(request.getOfferName());
        offer.setOfferDescription(request.getOfferDescription());
        offer.setOfferDiscount(request.getOfferDiscount());
        offer.setOfferType(request.getOfferType());
        offer.setStartDate(request.getStartDate());
        offer.setEndDate(request.getEndDate());
        offer.setIsActive(request.getIsActive());
        offer.setMinimumOrderValue(request.getMinimumOrderValue());
        offer.setMaximumDiscountAmount(request.getMaximumDiscountAmount());
        offer.setOfferCode(request.getOfferCode());
        offer.setTermsAndConditions(request.getTermsAndConditions());
        offer.setApplicableDays(request.getApplicableDays());
        offer.setRestaurant(restaurant);
        offer.setCreatedAt(LocalDateTime.now());
        offer.setUpdatedAt(LocalDateTime.now());

        Offer savedOffer = offerRepository.save(offer);
        return ResponseEntity.ok(new CommonResponse(200, "Offer created successfully", true));
    }

    public ResponseEntity<CommonResponse> updateOffer(Long id, OfferRequest request) {
        Offer existingOffer = offerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Offer not found"));

        if (!existingOffer.getRestaurant().getId().equals(request.getRestaurantId())) {
            throw new EntityNotFoundException("Not authorized to update this offer");
        }

        existingOffer.setOfferName(request.getOfferName());
        existingOffer.setOfferDescription(request.getOfferDescription());
        existingOffer.setOfferDiscount(request.getOfferDiscount());
        existingOffer.setOfferType(request.getOfferType());
        existingOffer.setStartDate(request.getStartDate());
        existingOffer.setEndDate(request.getEndDate());
        existingOffer.setIsActive(request.getIsActive());
        existingOffer.setMinimumOrderValue(request.getMinimumOrderValue());
        existingOffer.setMaximumDiscountAmount(request.getMaximumDiscountAmount());
        existingOffer.setOfferCode(request.getOfferCode());
        existingOffer.setTermsAndConditions(request.getTermsAndConditions());
        existingOffer.setApplicableDays(request.getApplicableDays());
        existingOffer.setUpdatedAt(LocalDateTime.now());

        Offer updatedOffer = offerRepository.save(existingOffer);

        return ResponseEntity.ok(new CommonResponse(200, "Offer updated successfully", true));
    }


    public void deleteOffer(Long id) {
        Offer offer = offerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Offer not found"));
        offerRepository.delete(offer);
    }

    public ResponseEntity<CommonResponse> getRestaurantOffers(Long restaurantId, Boolean activeOnly,
                                                   OfferType offerType) {
        List<Offer> offers;
        if (offerType != null) {
            if (activeOnly) {
                offers = offerRepository
                        .findByRestaurantIdAndIsActiveTrueAndOfferTypeAndEndDateAfter(restaurantId, offerType,LocalDateTime.now());
            } else {
                offers = offerRepository
                        .findByRestaurantIdAndOfferTypeAndEndDateAfter(restaurantId, offerType,LocalDateTime.now());
            }
        } else {
            if (activeOnly) {
                offers = offerRepository
                        .findByRestaurantIdAndIsActiveTrueAndEndDateAfter(restaurantId,LocalDateTime.now());
            } else {
                offers = offerRepository.findByRestaurantIdAndEndDateAfter(restaurantId,LocalDateTime.now());
            }
        }
        return ResponseEntity.ok(new CommonResponse(200, "Offers retrieved successfully", OfferResponse.convertToResponse(offers)));
    }
}