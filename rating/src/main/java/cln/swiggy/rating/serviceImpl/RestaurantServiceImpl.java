package cln.swiggy.rating.serviceImpl;

import cln.swiggy.rating.exception.ResourceNotFoundException;
import cln.swiggy.rating.model.RestaurantRating;
import cln.swiggy.rating.model.request.RatingRequest;
import cln.swiggy.rating.model.response.CommonResponse;
import cln.swiggy.rating.repository.RestaurantRatingRepository;
import cln.swiggy.rating.service.RestaurantService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    RestaurantRatingRepository ratingRepository;
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Override
    public ResponseEntity<CommonResponse> getRatingById(Long ratingId) {
            RestaurantRating rating = ratingRepository.findById(ratingId)
                    .orElseThrow(() -> new ResourceNotFoundException("Rating not found with id: " + ratingId));

            return ResponseEntity.ok( new CommonResponse(
                    HttpStatus.OK.value(),
                    "Rating retrieved successfully",
                    rating
            ));
    }

    @Override
    public ResponseEntity<CommonResponse> createRating(Long restaurantId, RatingRequest request)
    {
        Boolean isUserExists = (Boolean) rabbitTemplate.convertSendAndReceive("user_exchange","user_restaurant_key", request.getUserId());

        if (Boolean.FALSE.equals(isUserExists)) {
            CommonResponse response = new CommonResponse(
                    HttpStatus.BAD_REQUEST.value(),"User does not exist", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        Boolean isUserElement = (Boolean) rabbitTemplate.convertSendAndReceive("restaurant_exchange","restaurant_order_key", request.getUserId());

        if (Boolean.FALSE.equals(isUserElement)) {
            CommonResponse response = new CommonResponse(
                    HttpStatus.BAD_REQUEST.value(),"Restaurant does not exist", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }


        Optional<RestaurantRating> existingRating = ratingRepository
                    .findByUserIdAndRestaurantId(request.getUserId(), restaurantId);

            if (existingRating.isPresent()) {
                CommonResponse response = new CommonResponse(
                        HttpStatus.CONFLICT.value(),"User has already rated this restaurant", existingRating.get());
                return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
            }

            RestaurantRating rating = new RestaurantRating();
            rating.setRestaurantId(restaurantId);
            rating.setUserId(request.getUserId());
            rating.setRating(request.getRating());
            rating.setComment(request.getComment());
            rating.setCreatedAt(LocalDateTime.now());

            RestaurantRating savedRating = ratingRepository.save(rating);

            CommonResponse response = new CommonResponse(
                    HttpStatus.CREATED.value(),
                    "Rating created successfully",
                    savedRating
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public ResponseEntity<CommonResponse> updateRating(Long ratingId, RatingRequest request) {

            Boolean isUserElement = (Boolean) rabbitTemplate.convertSendAndReceive("restaurant_exchange","restaurant_order_key", request.getUserId());

            if (Boolean.FALSE.equals(isUserElement)) {
                CommonResponse response = new CommonResponse(
                        HttpStatus.BAD_REQUEST.value(),"Restaurant does not exist", null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            RestaurantRating existingRating = ratingRepository.findById(ratingId)
                    .orElseThrow(() -> new ResourceNotFoundException("Rating not found with id: " + ratingId));

            if (!existingRating.getUserId().equals(request.getUserId())) {
                CommonResponse response = new CommonResponse(
                        HttpStatus.CONFLICT.value(),
                        "User is not able to update this rating",
                        null
                );
                return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
            }

            existingRating.setRating(request.getRating());
            existingRating.setComment(request.getComment());
            existingRating.setUpdatedAt(LocalDateTime.now());

            RestaurantRating updatedRating = ratingRepository.save(existingRating);

            return ResponseEntity.ok( new CommonResponse(
                    HttpStatus.OK.value(),"Rating updated successfully", updatedRating));
    }

    @Override
    public ResponseEntity<CommonResponse> getRatingsByUserId(Long userId) {
            List<RestaurantRating> ratings = ratingRepository.findByUserId(userId);

            return ResponseEntity.ok(new CommonResponse(
                    HttpStatus.OK.value(),"Ratings retrieved successfully", ratings));
    }
}