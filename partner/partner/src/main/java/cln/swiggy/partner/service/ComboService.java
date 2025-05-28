package cln.swiggy.partner.service;

import cln.swiggy.partner.model.request.ComboOfferRequest;
import cln.swiggy.partner.model.response.CommonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ComboService {

    ResponseEntity<CommonResponse> addCombo(List<MultipartFile> images, ComboOfferRequest request);
    ResponseEntity<CommonResponse> deleteCombo(Long restaurantId, Long comboId);
    ResponseEntity<CommonResponse> getCombosByRestaurantId(Long restaurantId);
    ResponseEntity<CommonResponse> getAllCombos();
    ResponseEntity<CommonResponse> updateCombo(List<MultipartFile> images, ComboOfferRequest request, Long comboId);
    ResponseEntity<CommonResponse> mostTrendingCombos();
}
