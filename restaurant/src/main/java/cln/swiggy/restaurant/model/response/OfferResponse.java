package cln.swiggy.restaurant.model.response;

import cln.swiggy.restaurant.model.Offer;
import cln.swiggy.restaurant.model.enums.OfferType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OfferResponse {
    private String offerName;
    private String offerDescription;
    private Double offerDiscount;
    private OfferType offerType;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Boolean isActive;
    private Double minimumOrderValue;
    private Double maximumDiscountAmount;
    private String offerCode;
    private String termsAndConditions;
    private String applicableDays;


    public static OfferResponse convertToResponse(Offer offer) {
        OfferResponse response = new OfferResponse();
        response.setOfferName(offer.getOfferName());
        response.setOfferDescription(offer.getOfferDescription());
        response.setOfferDiscount(offer.getOfferDiscount());
        response.setOfferType(offer.getOfferType());
        response.setStartDate(offer.getStartDate());
        response.setEndDate(offer.getEndDate());
        response.setIsActive(offer.getIsActive());
        response.setMinimumOrderValue(offer.getMinimumOrderValue());
        response.setMaximumDiscountAmount(offer.getMaximumDiscountAmount());
        response.setOfferCode(offer.getOfferCode());
        response.setTermsAndConditions(offer.getTermsAndConditions());
        response.setApplicableDays(offer.getApplicableDays());
        return response;
    }

    public static List<OfferResponse> convertToResponse(List<Offer> offers) {
        return offers.stream()
                .map(OfferResponse::convertToResponse)
                .toList();
    }
 }