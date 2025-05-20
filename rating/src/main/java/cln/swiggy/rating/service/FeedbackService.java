package cln.swiggy.rating.service;

import cln.swiggy.rating.model.request.FeedbackRequest;
import cln.swiggy.rating.model.response.CommonResponse;
import org.springframework.http.ResponseEntity;

public interface FeedbackService {

    public ResponseEntity<CommonResponse> createFeedback(FeedbackRequest request);

    public ResponseEntity<CommonResponse> getFeedbacksByUser(Long userId);

    public ResponseEntity<CommonResponse> getFeedbacksByRestaurant(Long restaurantId);
}