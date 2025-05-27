package cln.swiggy.restaurant.model.response;

import cln.swiggy.restaurant.model.Category;
import lombok.Data;

@Data
public class CategoryResponse {
    private Long id;
    private String name;
    private String description;

    public static CategoryResponse convertToResponse(Category category) {
        CategoryResponse response = new CategoryResponse();
        response.setName(category.getName());
        response.setDescription(category.getDescription());
        return response;
    }
}