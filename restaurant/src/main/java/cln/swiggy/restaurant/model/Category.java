package cln.swiggy.restaurant.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
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

    public Category(String name) {
        this.name = name;
    }
}
