package cln.swiggy.partner.serviceImpl;

import cln.swiggy.partner.model.Menu;
import cln.swiggy.partner.model.Offer;
import cln.swiggy.partner.model.request.ComboOfferRequest;
import cln.swiggy.partner.model.response.ComboResponse;
import cln.swiggy.partner.model.response.CommonResponse;
import cln.swiggy.partner.repository.MenuRepository;
import cln.swiggy.partner.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import cln.swiggy.partner.model.Combo;
import cln.swiggy.partner.model.Restaurant;
import cln.swiggy.partner.repository.ComboRepository;
import cln.swiggy.partner.repository.RestaurantRepository;
import cln.swiggy.partner.service.ComboService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ComboServiceImpl implements ComboService {

    @Autowired ComboRepository comboRepository;
    @Autowired MenuRepository menuRepository;
    @Autowired OfferRepository offerRepository;
    @Autowired RestaurantRepository restaurantRepository;
    @Autowired ComboResponse comboResponse;

    @Override
    public ResponseEntity<CommonResponse> addCombo(List<MultipartFile> images, ComboOfferRequest request) {
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(request.getRestaurantId());
        if (restaurantOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new CommonResponse(404,"Restaurant not found", false));
        }

        List<Menu> menus = new ArrayList<>();
        List<Offer> offers = new ArrayList<>();

        for(Long m : request.getItemIds()){
            Optional<Menu> menu = menuRepository.findById(m);
            if (menu.isPresent()) {
                menus.add(menu.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new CommonResponse(404,"Menu not found with id " + m, false));
            }
        }
        for(Long o : request.getOfferIds()){
            Optional<Offer> offer = offerRepository.findById(o);
            if (offer.isPresent()) {
                offers.add(offer.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new CommonResponse(404,"Offer not found with id " + o, false));
            }
        }

        Restaurant restaurant = restaurantOptional.get();
        Combo combo = new Combo();
        combo.setRestaurant(restaurant);
        combo.setMenus(menus);
        combo.setOffers(offers);
        combo.setName(request.getName());
        combo.setDescription(request.getDescription());
        combo.setPrice(request.getPrice());
        combo.setComboType(request.getComboType());
        combo.setImage(saveUploadedImages(images));
        combo.setCreatedAt(LocalDateTime.now());
        combo.setUpdatedAt(LocalDateTime.now());

        comboRepository.save(combo);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CommonResponse(201,"Combo added successfully", true));
    }

    @Override
    public ResponseEntity<CommonResponse> deleteCombo(Long restaurantId, Long comboId) {
        Optional<Combo> comboOptional = comboRepository.findByIdAndRestaurantId(comboId, restaurantId);
        if (comboOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new CommonResponse(404,"Combo not found", false));
        }

        comboRepository.delete(comboOptional.get());
        return ResponseEntity.ok(new CommonResponse(200,"Combo deleted successfully", true));
    }

    @Override
    public ResponseEntity<CommonResponse> getCombosByRestaurantId(Long restaurantId) {
        List<Combo> comboList = comboRepository.findAllByRestaurantId(restaurantId);

        if (comboList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new CommonResponse(404,"No combos found for the restaurant", false));
        }

        return ResponseEntity.ok(new CommonResponse(200,"Combos retrieved successfully", comboResponse.convertToResponse(comboList)));
    }

    @Override
    public ResponseEntity<CommonResponse> getAllCombos() {
        List<Combo> comboList = comboRepository.findAll();
        if (comboList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new CommonResponse(404,"No combos found", false));
        }

        return ResponseEntity.ok(new CommonResponse(200,"All combos retrieved successfully", comboList));
    }

    @Override
    public ResponseEntity<CommonResponse> updateCombo(List<MultipartFile> images, ComboOfferRequest request, Long comboId) {
        Optional<Combo> comboOptional = comboRepository.findById(comboId);
        if (comboOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new CommonResponse(404,"Combo not found", false));
        }

        List<Menu> menus = new ArrayList<>();
        List<Offer> offers = new ArrayList<>();

        for(Long m : request.getItemIds()){
            Optional<Menu> menu = menuRepository.findById(m);
            if (menu.isPresent()) {
                menus.add(menu.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new CommonResponse(404,"Menu not found with id " + m, false));
            }
        }
        for(Long o : request.getOfferIds()){
            Optional<Offer> offer = offerRepository.findById(o);
            if (offer.isPresent()) {
                offers.add(offer.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new CommonResponse(404,"Offer not found with id " + o, false));
            }
        }

        Combo combo = comboOptional.get();
        combo.setName(request.getName());
        combo.setMenus(menus);
        combo.setOffers(offers);
        combo.setDescription(request.getDescription());
        combo.setPrice(request.getPrice());
        combo.setComboType(request.getComboType());
        combo.setImage(saveUploadedImages(images));
        combo.setUpdatedAt(LocalDateTime.now());

        comboRepository.save(combo);

        return ResponseEntity.ok(new CommonResponse(200,"Combo updated successfully", true));
    }

    @Override
    public ResponseEntity<CommonResponse> mostTrendingCombos() {
        List<Combo> trendingCombos = comboRepository.findAllByIsAvailableTrueOrderByTotalOrdersDesc();

        List<ComboResponse> responses = comboResponse.convertToResponse(trendingCombos.stream().limit(5).toList());

        return ResponseEntity.ok(new CommonResponse(200,"Trending combos retrieved successfully", trendingCombos));
    }

    private List<String> saveUploadedImages(List<MultipartFile> images) {
        List<String> imageUrls = new ArrayList<>();
        if (images != null) {
            for (MultipartFile image : images) {
//                String key = storageService.uploadFile(file);
                    imageUrls.add("demo.jpg");
            }
        }
        return imageUrls;
    }
}