package cln.swiggy.restaurant.serviceImpl.otherImple;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ImageUtils {

    @Value("${swiggy.upload.base-path}")
    private String basePath;

    public String saveImage(MultipartFile file, String category) {
        String validationResult = ImageValidation.validateImage(file);
        if (validationResult != null) {
            throw new IllegalArgumentException(validationResult);
        }

        try {
            String categoryPath = basePath + File.separator + category;
            File directory = new File(categoryPath);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String newFilename = UUID.randomUUID().toString() + extension;

            String fullPath = categoryPath + File.separator + newFilename;
            Path path = Paths.get(fullPath);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            return category + File.separator + newFilename;

        } catch (IOException e) {
            throw new RuntimeException("Failed to save image: " + e.getMessage());
        }
    }

    public List<String> saveImages(List<MultipartFile> files, String category) {
        List<String> validationErrors = ImageValidation.validateImages(files);
        if (validationErrors != null && !validationErrors.isEmpty()) {
            throw new IllegalArgumentException("Image validation failed: " + String.join(", ", validationErrors));
        }

        return files.stream()
                .map(file -> saveImage(file, category))
                .collect(Collectors.toList());
    }

    public boolean deleteImage(String imagePath, String category) {
        try {
            if (imagePath == null || imagePath.isEmpty()) {
                return false;
            }

            if (!imagePath.startsWith(category + File.separator)) {
                throw new SecurityException("Invalid image path for category: " + category);
            }

            String fullPath = basePath + File.separator + imagePath;
            return Files.deleteIfExists(Paths.get(fullPath));

        } catch (IOException e) {
            throw new RuntimeException("Failed to delete image: " + e.getMessage());
        }
    }

    public void deleteImages(List<String> imagePaths, String category) {
        if (imagePaths != null && !imagePaths.isEmpty()) {
            imagePaths.forEach(path -> deleteImage(path, category));
        }
    }

    public String updateImage(String oldImagePath, MultipartFile newFile, String category) {
        String validationResult = ImageValidation.validateImage(newFile);
        if (validationResult != null) {
            throw new IllegalArgumentException(validationResult);
        }

        if (oldImagePath != null && !oldImagePath.isEmpty()) {
            deleteImage(oldImagePath, category);
        }

        return saveImage(newFile, category);
    }
}