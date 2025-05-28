package cln.swiggy.restaurant.serviceImpl;

import cln.swiggy.restaurant.exception.ResourceNotFoundException;
import cln.swiggy.restaurant.model.Category;
import cln.swiggy.restaurant.model.response.CommonResponse;
import cln.swiggy.restaurant.repository.CategoryRepository;
import cln.swiggy.restaurant.repository.OrdersCategoryRepository;
import cln.swiggy.restaurant.repository.RestaurantRepository;
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
}