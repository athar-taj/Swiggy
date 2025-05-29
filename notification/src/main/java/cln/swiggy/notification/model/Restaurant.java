package cln.swiggy.notification.model;

import cln.swiggy.notification.model.enums.RestaurantType;
import cln.swiggy.notification.serviceImpl.OtherService.StringListConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long ownerId;

    private String name;

    private String contactNo;

    private String email;

    private String outlet;

    private String description;

    private String logo;

    @Enumerated(EnumType.STRING)
    private RestaurantType restaurantType;

    @Convert(converter = StringListConverter.class)
    @Column(columnDefinition = "TEXT")
    private List<String> openDays;

    private LocalTime startTime;

    private LocalTime endTime;

    private LocalTime avgDeliveryTime;

    private Double costForTwo;

    private int totalRating;

    private Double rating;

    private Boolean isAvailable = true;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @JsonIgnore
    @OneToMany(mappedBy = "restaurant",cascade = CascadeType.ALL)
    private List<Address> addresses = new ArrayList<>();

}