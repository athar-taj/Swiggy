package cln.swiggy.partner.model.response;

import cln.swiggy.partner.model.Menu;
import cln.swiggy.partner.model.MenuImage;
import cln.swiggy.partner.model.enums.MenuType;
import cln.swiggy.partner.repository.MenuImagesRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Component
public class MenuResponse {

        @Autowired MenuImagesRepository menuImagesRepository;

        private Long id;
        private String name;
        private String description;
        private double price;
        private double discount;
        private Boolean isAvailable;
        private List<String> images;
        private int TotalRating;
        private double rating;
        private LocalTime FastDeliveryTime;
        private MenuType menuType;

        public MenuResponse convertToResponse(Menu menu) {

            MenuResponse response = new MenuResponse();
            response.setName(menu.getName());
            response.setDescription(menu.getDescription());
            response.setPrice(menu.getPrice());
            response.setDiscount(menu.getDiscount());
            response.setMenuType(menu.getMenuType());
            response.setIsAvailable(menu.getIsAvailable());
            response.setTotalRating(menu.getTotalRating());
            response.setRating(menu.getRating());
            response.setFastDeliveryTime(menu.getRestaurant().getAvgDeliveryTime());

            List<MenuImage> menusImages = menuImagesRepository.findByMenu(menu);

            response.setImages(menusImages.stream().map(MenuImage::getImage).toList());
            return response;
        }

        public List<MenuResponse> convertToResponse(List<Menu> menus){
            ArrayList<MenuResponse> response = new ArrayList<>();
            for (Menu menu : menus){
                response.add(convertToResponse(menu));
            }
            return response;
        }

}
