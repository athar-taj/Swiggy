package cln.swiggy.partner.service;


import cln.swiggy.partner.model.request.RestaurantMenuImageRequest;
import cln.swiggy.partner.model.response.CommonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface RestaurantMenuImageService {
    ResponseEntity<CommonResponse> addMenuImages(RestaurantMenuImageRequest request);
    ResponseEntity<CommonResponse> updateMenuImage(Long imageId, MultipartFile image);
    ResponseEntity<CommonResponse> getMenuImages(Long restaurantId);
    ResponseEntity<CommonResponse> deleteMenuImage(Long imageId);
}