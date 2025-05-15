package cln.swiggy.restaurant.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private String image;

    @ManyToMany(mappedBy = "categories")
    private List<Restaurant> restaurants = new ArrayList<>();
}
