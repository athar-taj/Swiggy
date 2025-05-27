package cln.swiggy.partner.search.model;

import cln.swiggy.partner.model.response.CategoryResponse;
import cln.swiggy.partner.model.response.MenuResponse;
import cln.swiggy.partner.model.response.RestaurantResponse;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;


@Data
@Document(indexName = "restaurants")
public class ElasticObject {
    @Id
    private String id;

    @Field(type = FieldType.Object)
    private RestaurantResponse restaurant;

    @Field(type = FieldType.Object)
    private MenuResponse menu;

    @Field(type = FieldType.Object)
    private CategoryResponse category;

    @Field(type = FieldType.Text)
    private String label;

    @Field(type = FieldType.Long)
    private Long elementId;
}

