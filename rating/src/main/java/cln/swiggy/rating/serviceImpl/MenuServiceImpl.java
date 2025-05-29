package cln.swiggy.rating.serviceImpl;

import cln.swiggy.rating.exception.ResourceNotFoundException;
import cln.swiggy.rating.model.MenuRating;
import cln.swiggy.rating.model.request.RatingRequest;
import cln.swiggy.rating.model.response.CommonResponse;
import cln.swiggy.rating.repository.MenuRatingRepository;
import cln.swiggy.rating.service.MenuService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    MenuRatingRepository ratingRepository;
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Override
    public ResponseEntity<CommonResponse> getRatingById(Long ratingId) {
            MenuRating rating = ratingRepository.findById(ratingId)
                    .orElseThrow(() -> new ResourceNotFoundException("Rating not found with id: " + ratingId));

            return ResponseEntity.ok(new CommonResponse(
                    HttpStatus.OK.value(),"Rating retrieved successfully", rating));
    }

    @Override
    public ResponseEntity<CommonResponse> createRating(RatingRequest request) {

        Boolean isUserElement = (Boolean) rabbitTemplate.convertSendAndReceive("user_exchange","menu_rating_key", request.getUserId());

        if (Boolean.FALSE.equals(isUserElement)) {
            CommonResponse response = new CommonResponse(
                    HttpStatus.BAD_REQUEST.value(),"User does not exist", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        Optional<MenuRating> existingRating = ratingRepository.findByUserIdAndMenuId(
                request.getUserId(), request.getElementId());

        if (existingRating.isPresent()) {
            CommonResponse response = new CommonResponse(
                    HttpStatus.CONFLICT.value(),
                    "User has already rated this menu item",
                    existingRating.get()
            );
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }

        MenuRating rating = new MenuRating();
        rating.setMenuId(request.getElementId());
        rating.setUserId(request.getUserId());
        rating.setRating(request.getRating());
        rating.setComment(request.getComment());
        rating.setCreatedAt(LocalDateTime.now());


        HashMap<String, Object> responseMap = new HashMap<>();
        responseMap.put("rating", request.getRating());
        responseMap.put("menuId", request.getElementId());

        Boolean result = (Boolean) rabbitTemplate.convertSendAndReceive("menu_exchange", "menu_rating_key", responseMap);

        if(Boolean.TRUE.equals(result)) {
            MenuRating savedRating = ratingRepository.save(rating);

            return ResponseEntity.status(HttpStatus.CREATED).body(new CommonResponse(
                    HttpStatus.CREATED.value(), "Rating created successfully", savedRating));
        }
        else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new CommonResponse(
                    500,"Something Went Wrong While Menu Rating",null));

        }
    }

    @Override
    public ResponseEntity<CommonResponse> updateRating(Long ratingId, RatingRequest request) {

        Boolean isUserElement = (Boolean) rabbitTemplate.convertSendAndReceive("user_exchange","rating_user_key", request.getUserId());

        if (Boolean.FALSE.equals(isUserElement)) {
            CommonResponse response = new CommonResponse(
                    HttpStatus.BAD_REQUEST.value(),"User does not exist", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        MenuRating existingRating = ratingRepository.findById(ratingId)
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

            MenuRating updatedRating = ratingRepository.save(existingRating);

            return ResponseEntity.ok(new CommonResponse(HttpStatus.OK.value(),"Rating updated successfully", updatedRating));
  }

    @Override
    public ResponseEntity<CommonResponse> getRatingsByUserId(Long userId) {
            List<MenuRating> ratings = ratingRepository.findByUserId(userId);

            return ResponseEntity.ok( new CommonResponse(
                    HttpStatus.OK.value(),"Ratings retrieved successfully", ratings));
    }
}