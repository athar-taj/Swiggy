package cln.swiggy.restaurant.model;

import cln.swiggy.restaurant.model.enums.MenuType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private double price;

    private double discount;

    private int TotalRating;

    private double rating;

    private int totalOrders;

    @Enumerated(EnumType.STRING)
    private MenuType menuType;

    private Boolean isAvailable = true;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @JsonIgnore
    @OneToMany(mappedBy = "menu")
    private List<Wishlist> wishlists;

    @JsonIgnore
    @OneToMany(mappedBy = "cartMenu")
    private List<Cart> carts;

    @JsonIgnore
    @OneToMany(mappedBy = "menu")
    private List<MenuImage> images;

}