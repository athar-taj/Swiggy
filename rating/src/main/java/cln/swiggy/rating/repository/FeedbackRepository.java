package cln.swiggy.rating.repository;

import cln.swiggy.rating.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByRestaurantId(Long restaurantId);
    List<Feedback> findByUserId(Long userId);
}