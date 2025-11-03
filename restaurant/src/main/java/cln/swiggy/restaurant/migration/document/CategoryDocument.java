package cln.swiggy.restaurant.migration.document;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "category")
public class CategoryDocument {

    @Id
    private String id;

    private Long mysqlId;

    private String name;
    private String description;
    private String image;

    @DBRef
    private List<RestaurantDocument> restaurants;


    private List<MenuDocument> menus;
}
