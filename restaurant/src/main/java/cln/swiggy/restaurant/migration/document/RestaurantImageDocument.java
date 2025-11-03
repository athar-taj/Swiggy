package cln.swiggy.restaurant.migration.document;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "restaurant_images")
public class RestaurantImageDocument {

    @Id
    private String id;

    private Long mysqlId;

    @DBRef
    private RestaurantDocument restaurant;

    private String image;
    private boolean isLogoImage = false;
}

