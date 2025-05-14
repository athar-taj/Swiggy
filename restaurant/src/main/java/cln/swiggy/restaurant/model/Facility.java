package cln.swiggy.restaurant.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

public class Facility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Facility name is required")
    @Column(name = "facility_name", nullable = false)
    private String facilityName;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

}
