package cln.swiggy.partner.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class RestaurantMenuImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant", nullable = false)
    private Restaurant restaurant;

    @Column(nullable = false)
    private String image;
}
