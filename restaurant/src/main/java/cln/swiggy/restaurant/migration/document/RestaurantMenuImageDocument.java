package cln.swiggy.restaurant.migration.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "restaurant_menu_images")
public class RestaurantMenuImageDocument {

    @Id
    private String id;

    private String image;

    @DBRef
    private RestaurantDocument restaurant;
}
