package cln.swiggy.restaurant.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    private String outlet;

    private Double latitude;

    private Double longitude;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String city;

    private String state;

    @Column(nullable = false)
    private String pincode;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
