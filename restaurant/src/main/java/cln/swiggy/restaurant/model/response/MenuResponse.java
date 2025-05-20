package cln.swiggy.restaurant.model.response;

import cln.swiggy.restaurant.model.Menu;
import cln.swiggy.restaurant.model.MenuImage;
import cln.swiggy.restaurant.repository.MenuImagesRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

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

        public static MenuResponse convertToResponse(Menu menu,MenuImagesRepository menuImagesRepository) {

            MenuResponse response = new MenuResponse();
            response.setName(menu.getName());
            response.setDescription(menu.getDescription());
            response.setPrice(menu.getPrice());
            response.setDiscount(menu.getDiscount());
            response.setIsAvailable(menu.getIsAvailable());

            List<MenuImage> menusImages = menuImagesRepository.findByMenu(menu);

            response.setImages(menusImages.stream().map(MenuImage::getImage).toList());
            return response;
        }

    }
