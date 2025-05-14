package cln.swiggy.restaurant.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "restaurant_images")
public class RestaurantImages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @Column(nullable = false)
    private String image;
}
