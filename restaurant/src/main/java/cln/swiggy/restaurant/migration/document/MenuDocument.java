package cln.swiggy.restaurant.migration.document;

import cln.swiggy.restaurant.model.enums.MenuType;
import jakarta.persistence.Column;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "menus")
public class MenuDocument {

    @Id
    private String id;

    private String name;
    private String description;
    private double price;
    private double discount;
    private int totalRating;
    private double rating;
    private int totalOrders;
    private MenuType menuType;
    private Boolean isAvailable = true;
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    @DBRef
    private RestaurantDocument restaurant;

    @DBRef
    private CategoryDocument category;


    @DBRef
    private List<RestaurantMenuImageDocument> images;
}

