package cln.swiggy.restaurant.serviceImpl;

import cln.swiggy.restaurant.exception.ResourceNotFoundException;
import cln.swiggy.restaurant.model.Category;
import cln.swiggy.restaurant.model.request.CategoryRequest;
import cln.swiggy.restaurant.model.response.CommonResponse;
import cln.swiggy.restaurant.repository.CategoryRepository;
import cln.swiggy.restaurant.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired CategoryRepository categoryRepository;

    @Value("${swiggy.category.image.path}")
    private String categoryImagePath;

    @Override
    public ResponseEntity<CommonResponse> createCategory(CategoryRequest request) throws IOException {
            Category category = new Category();
            category.setName(request.getName());
            category.setDescription(request.getDescription());

            if (request.getImage() != null && !request.getImage().isEmpty()) {
                String fileName = saveImage(request.getImage());
                category.setImage(fileName);
            }

            Category savedCategory = categoryRepository.save(category);

            return ResponseEntity.ok(new CommonResponse(HttpStatus.CREATED.value(),"Category created successfully", savedCategory));
    }

    @Override
    public ResponseEntity<CommonResponse> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
            return ResponseEntity.ok(new CommonResponse(
                    HttpStatus.OK.value(),"Categories retrieved successfully", categories));
    }

    @Override
    public ResponseEntity<CommonResponse> updateCategory(Long id, CategoryRequest request) throws IOException{
            Category category = categoryRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));

            category.setDescription(request.getDescription());

            if (request.getImage() != null && !request.getImage().isEmpty()) {
                if (category.getImage() != null) {
                    deleteImage(category.getImage());
                }

                String fileName = saveImage(request.getImage());
                category.setImage(fileName);
            }

            Category updatedCategory = categoryRepository.save(category);

            return ResponseEntity.ok(new CommonResponse(
                    HttpStatus.OK.value(),"Category updated successfully", updatedCategory));
    }

    @Override
    public ResponseEntity<CommonResponse> deleteCategory(Long id) throws IOException{
            Category category = categoryRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));

            if (category.getImage() != null) {
                deleteImage(category.getImage());
            }

            categoryRepository.delete(category);

            return ResponseEntity.ok(new CommonResponse(
                    HttpStatus.OK.value(),"Category deleted successfully",null));
       }

    @Override
    public ResponseEntity<CommonResponse> removeCategoryFromRestaurant(Long categoryId, Long restaurantId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryId));
        if (!category.getRestaurants().contains(restaurantId)) {
            throw new IllegalArgumentException("Category does not belong to the restaurant with id: " + restaurantId);
        }
        category.getRestaurants().remove(restaurantId);
        categoryRepository.save(category);
        return ResponseEntity.ok(new CommonResponse(
                HttpStatus.OK.value(), "Category removed from restaurant successfully", null));
    }

    private String saveImage(MultipartFile file) throws IOException {
        Path uploadPath = Paths.get(categoryImagePath);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename != null ?
                originalFilename.substring(originalFilename.lastIndexOf(".")) : "";
        String filename = UUID.randomUUID().toString() + extension;

        Path filePath = uploadPath.resolve(filename);
        Files.copy(file.getInputStream(), filePath);

        return filename;
    }

    private void deleteImage(String filename) {
        try {
            Path filePath = Paths.get(categoryImagePath).resolve(filename);
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            System.out.println("Error deleting image: " + filename + " " + e);
        }
    }
}