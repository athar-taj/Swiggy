package cln.swiggy.rating.service;

import cln.swiggy.rating.model.request.RatingRequest;
import cln.swiggy.rating.model.response.CommonResponse;
import org.springframework.http.ResponseEntity;

public interface RestaurantService {
    ResponseEntity<CommonResponse> getRatingById(Long ratingId);
    ResponseEntity<CommonResponse> createRating(RatingRequest request);
    ResponseEntity<CommonResponse> updateRating(Long ratingId, RatingRequest request);
    ResponseEntity<CommonResponse> getRatingsByUserId(Long userId);
}
