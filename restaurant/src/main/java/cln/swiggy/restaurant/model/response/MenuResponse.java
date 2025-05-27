package cln.swiggy.restaurant.model.response;

import cln.swiggy.restaurant.model.Menu;
import cln.swiggy.restaurant.model.MenuImage;
import cln.swiggy.restaurant.model.enums.MenuType;
import cln.swiggy.restaurant.repository.MenuImagesRepository;
import cln.swiggy.restaurant.repository.MenuRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class MenuResponse {

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

        public static MenuResponse convertToResponse(Menu menu,MenuImagesRepository menuImagesRepository) {

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

        public static List<MenuResponse> convertToResponse(List<Menu> menus,MenuImagesRepository menuImagesRepository){
            ArrayList<MenuResponse> response = new ArrayList<>();
            for (Menu menu : menus){
                response.add(convertToResponse(menu,menuImagesRepository));
            }
            return response;
        }

}
