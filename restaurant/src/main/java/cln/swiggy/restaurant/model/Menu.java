package cln.swiggy.restaurant.model;

import cln.swiggy.restaurant.model.enums.MenuType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "menu")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "discount")
    private double discount;

    @Column(name = "total_rating")
    private int totalRating;

    @Column(name = "rating")
    private double rating;

    @Column(name = "total_orders")
    private int totalOrders;

    @Enumerated(EnumType.STRING)
    @Column(name = "menu_type")
    private MenuType menuType;

    @Column(name = "is_available")
    private Boolean isAvailable = true;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
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
