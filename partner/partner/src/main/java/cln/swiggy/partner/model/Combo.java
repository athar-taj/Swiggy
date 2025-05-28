package cln.swiggy.partner.model;

import cln.swiggy.partner.model.enums.MenuType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "combos")
public class Combo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "combo_menus",
            joinColumns = @JoinColumn(name = "combo_id"),
            inverseJoinColumns = @JoinColumn(name = "menu_id"))
    private List<Menu> menus;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private Double price;

    @ElementCollection
    @CollectionTable(name = "combo_images", joinColumns = @JoinColumn(name = "combo_id"))
    @Column(name = "image_url")
    private List<String> image;

    @Column(nullable = false)
    private Boolean isAvailable = true;

    private int totalRating;

    private Double rating = 0.0;

    @Enumerated(EnumType.STRING)
    private MenuType comboType;

    private int totalOrders;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "combo_offers",
            joinColumns = @JoinColumn(name = "combo_id"),
            inverseJoinColumns = @JoinColumn(name = "offer_id"))
    private List<Offer> offers;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}