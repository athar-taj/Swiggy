package cln.swiggy.restaurant.model.response;

import cln.swiggy.restaurant.model.Category;

public class CategoryResponse {
    private String name;
    private String description;
    private String image;

    public static CategoryResponse convertToResponse(Category category) {
        CategoryResponse response = new CategoryResponse();
        response.name = category.getName();
        response.description = category.getDescription();
        response.image = category.getImage();
        return response;
    }
}
