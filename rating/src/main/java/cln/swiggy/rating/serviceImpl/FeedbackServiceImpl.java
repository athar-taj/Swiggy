package cln.swiggy.rating.serviceImpl;

import cln.swiggy.rating.model.Feedback;
import cln.swiggy.rating.model.request.FeedbackRequest;
import cln.swiggy.rating.model.response.CommonResponse;
import cln.swiggy.rating.repository.FeedbackRepository;
import cln.swiggy.rating.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired FeedbackRepository feedbackRepository;
    @Autowired RabbitTemplate rabbitTemplate;

    public ResponseEntity<CommonResponse> createFeedback(FeedbackRequest request) {

        Boolean isUserExists = (Boolean) rabbitTemplate.convertSendAndReceive("user_exchange","user_restaurant_key", request.getUserId());

        if (Boolean.FALSE.equals(isUserExists)) {
            CommonResponse response = new CommonResponse(
                    HttpStatus.BAD_REQUEST.value(),"User does not exist", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }


        Boolean isUserElement = (Boolean) rabbitTemplate.convertSendAndReceive("restaurant_exchange","restaurant_order_key", request.getRestaurantId());

        if (Boolean.FALSE.equals(isUserElement)) {
            CommonResponse response = new CommonResponse(
                    HttpStatus.BAD_REQUEST.value(),"Restaurant does not exist", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        Feedback feedback = new Feedback();
        feedback.setUserId(request.getUserId());
        feedback.setRestaurantId(request.getRestaurantId());
        feedback.setFeedback(request.getFeedback());
        feedbackRepository.save(feedback);

        return ResponseEntity.ok(new CommonResponse(200, "Feedback created successfully", feedback));
    }

    public ResponseEntity<CommonResponse> getFeedbacksByRestaurant(Long restaurantId) {
        List<Feedback> feedbacks = feedbackRepository.findByRestaurantId(restaurantId);
        return ResponseEntity.ok(new CommonResponse(200, "Feedbacks retrieved successfully", feedbacks));
    }

    public ResponseEntity<CommonResponse> getFeedbacksByUser(Long userId) {
        List<Feedback> feedbacks = feedbackRepository.findByUserId(userId);
        return ResponseEntity.ok(new CommonResponse(200, "Feedbacks retrieved successfully", feedbacks));
    }
}