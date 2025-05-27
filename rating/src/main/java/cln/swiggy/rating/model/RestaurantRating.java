package cln.swiggy.rating.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class RestaurantRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long userId;
    @Column(nullable = false)
    private Long restaurantId;
    @Column(nullable = false)
    private int rating;
    @Column(nullable = false)
    private String comment;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
