package cln.swiggy.restaurant.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    @ManyToOne
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu cartMenu;

    private Integer quantity;
    private Double price;
    private Double totalPrice;
    private LocalDateTime createdAt;



}
