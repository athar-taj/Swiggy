package cln.swiggy.restaurant.serviceImpl;

import cln.swiggy.restaurant.exception.ResourceNotFoundException;
import cln.swiggy.restaurant.model.Category;
import cln.swiggy.restaurant.model.request.CategoryRequest;
import cln.swiggy.restaurant.model.response.CategoryResponse;
import cln.swiggy.restaurant.model.response.CommonResponse;
import cln.swiggy.restaurant.repository.CategoryRepository;
import cln.swiggy.restaurant.repository.OrdersCategoryRepository;
import cln.swiggy.restaurant.repository.RestaurantRepository;
import cln.swiggy.restaurant.search.model.ElasticObject;
import cln.swiggy.restaurant.search.repository.ElasticRepository;
import cln.swiggy.restaurant.service.CategoryService;
import com.aws.service.s3bucket.service.StorageService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired RestaurantRepository restaurantRepository;
    @Autowired CategoryRepository categoryRepository;
    @Autowired OrdersCategoryRepository ordersCategoryRepository;
    @Autowired ElasticRepository elasticRepository;

    @Value("${swiggy.category.image.path}")
    private String categoryImagePath;
    @Autowired
    StorageService storageService;

    @Override
    public ResponseEntity<CommonResponse> createCategory(CategoryRequest request) throws IOException {

        Category category = new Category();
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        category.setImage("demo.file");

        Category savedCategory = categoryRepository.save(category);

        ElasticObject elasticObject = new ElasticObject();
        elasticObject.setElementId(savedCategory.getId());
        elasticObject.setLabel("Category");
        elasticObject.setCategory(CategoryResponse.convertToResponse(savedCategory));
        elasticRepository.save(elasticObject);

        return ResponseEntity.ok(new CommonResponse(HttpStatus.CREATED.value(), "Category created successfully", savedCategory));
    }

    @Override
    public ResponseEntity<CommonResponse> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
            return ResponseEntity.ok(new CommonResponse(
                    HttpStatus.OK.value(),"Categories retrieved successfully", categories));
    }

    @Override
    public ResponseEntity<CommonResponse> getCategoryById(Long id){
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        return ResponseEntity.ok(new CommonResponse(
                HttpStatus.OK.value(),"Category retrieved successfully", category));
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

    @Override
    public ResponseEntity<CommonResponse> getTopCategories(int limit) {
        int resultLimit = limit <= 0 ? 8 : limit;
        List<String> categories = ordersCategoryRepository.getTopCategories(resultLimit);

        List<Category> topCategory = new ArrayList<>();

        for(String category : categories) {
            topCategory.add(categoryRepository.findByName(category));
        }
        return ResponseEntity.ok(new CommonResponse(
                HttpStatus.OK.value(),"Top categories retrieved successfully", topCategory));
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