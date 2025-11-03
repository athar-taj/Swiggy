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
@Document(collection = "facilities")
public class FacilityDocument {

    @Id
    private String id;

    private Long mysqlId;

    private String facilityName;
    private String description;

    @DBRef
    private RestaurantDocument restaurant;
}

