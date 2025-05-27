package cln.swiggy.partner.serviceImpl.otherImple;


import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

public class ImageValidation {
    
    private static final long MAX_SIZE_BYTES = 2 * 1024 * 1024;
    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList(".jpg", ".jpeg", ".png");
    private static final List<String> ALLOWED_CONTENT_TYPES = Arrays.asList("image/png","image/jpeg", "image/png");

    public static String validateImage(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return null;
        }

        if (file.getSize() > MAX_SIZE_BYTES) {
            return "Image size must not more then 2MB";
        }

        String originalFilename = file.getOriginalFilename();
        if (originalFilename != null) {
            String fileExtension = originalFilename.toLowerCase();
            boolean hasValidExtension = ALLOWED_EXTENSIONS.stream()
                    .anyMatch(fileExtension::endsWith);
            
            if (!hasValidExtension) {
                return "Allowed image formats are: JPG, JPEG, PNG";
            }
        }

        // Check content type
        String contentType = file.getContentType();
        if (contentType == null || !ALLOWED_CONTENT_TYPES.contains(contentType)) {
            return "Invalid image format";
        }

        return null;
    }

    public static List<String> validateImages(List<MultipartFile> images) {
        if (images == null || images.isEmpty()) {
            return null;
        }

        return images.stream()
                .map(img -> ImageValidation.validateImage(img))
                .filter(result -> result != null)
                .toList();
    }
}