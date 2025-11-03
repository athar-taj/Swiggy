package cln.swiggy.restaurant.migration.document;

import jakarta.persistence.Column;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "restaurant")
public class RestaurantDocument {
    @Id
    private String id;

    private Long mysqlId;

    private String name;
    private Long ownerId;
    private String description;
    private String contactNo;
    private String email;
    private String logo;
    private String restaurantType;
    private List<String> openDays = new ArrayList<>();
    private Boolean isAvailable = true;
    private Double rating = 0.0;
    private Integer totalRating = 0;
    private Double costForTwo = 0.0;
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    private List<AddressDocument> addresses = new ArrayList<>();
    private List<CategoryDocument> categories = new ArrayList<>();
    private List<OfferDocument> offers = new ArrayList<>();
    private List<RestaurantImageDocument> images = new ArrayList<>();

    private List<String> menuIds = new ArrayList<>();
    private List<String> facilityIds = new ArrayList<>();
}
