package cln.swiggy.restaurant.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    @ManyToMany(mappedBy = "categories")
    private List<Restaurant> restaurants = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "category")
    private List<Menu> menus = new ArrayList<>();
}
