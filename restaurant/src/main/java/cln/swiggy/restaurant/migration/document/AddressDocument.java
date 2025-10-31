package cln.swiggy.restaurant.migration.document;

import jakarta.persistence.Column;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "addresses")
public class AddressDocument {

    @Id
    private String id;

    private String outlet;
    private Double latitude;
    private Double longitude;
    private String address;
    private String city;
    private String state;
    private String pincode;
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    @DBRef
    private RestaurantDocument restaurant;
}
