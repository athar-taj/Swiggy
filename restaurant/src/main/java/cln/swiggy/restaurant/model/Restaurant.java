package cln.swiggy.restaurant.model;

import cln.swiggy.restaurant.model.enums.RestaurantType;
import cln.swiggy.restaurant.serviceImpl.otherImple.StringListConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "restaurant")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "owner_id")
    private Long ownerId;

    @Column(name = "name")
    private String name;

    @Column(name = "contact_no")
    private String contactNo;

    @Column(name = "email")
    private String email;

    @Column(name = "outlet")
    private String outlet;

    @Column(name = "description")
    private String description;

    @Column(name = "logo")
    private String logo;

    @Enumerated(EnumType.STRING)
    @Column(name = "restaurant_type")
    private RestaurantType restaurantType;

    @Convert(converter = StringListConverter.class)
    @Column(name = "open_days", columnDefinition = "TEXT")
    private List<String> openDays;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    @Column(name = "avg_delivery_time")
    private LocalTime avgDeliveryTime;

    @Column(name = "cost_for_two")
    private Double costForTwo;

    @Column(name = "total_rating", nullable = true)
    private Integer totalRating = 0;

    @Column(name = "rating")
    private Double rating;

    @Column(name = "is_available")
    private Boolean isAvailable = true;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "restaurant_category",
            joinColumns = @JoinColumn(name = "restaurant_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<RestaurantImages> images = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Facility> facilities = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Menu> menus = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Address> addresses = new ArrayList<>();

    @JsonIgnore
    @Transient
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Booking> booking = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<RestaurantMenuImage> restaurantMenuImages = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Offer> offers = new ArrayList<>();
}
