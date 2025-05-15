package cln.swiggy.restaurant.model.response;

import cln.swiggy.restaurant.model.Menu;
import cln.swiggy.restaurant.model.MenuImage;
import cln.swiggy.restaurant.repository.MenuImagesRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Data
public class MenuResponse {

    @Autowired
     static MenuImagesRepository menuImagesRepository;

        private Long id;
        private String name;
        private String description;
        private int price;
        private int discount;
        private Boolean isAvailable;
        private List<String> images;

        public static MenuResponse convertToResponse(Menu menu) {
            MenuResponse response = new MenuResponse();
            response.setName(menu.getName());
            response.setDescription(menu.getDescription());
            response.setPrice(menu.getPrice());
            response.setDiscount(menu.getDiscount());
            response.setIsAvailable(menu.getIsAvailable());

            List<MenuImage> menusImages = menuImagesRepository.findByMenuId(menu.getId());

            response.setImages(menusImages.stream().map(MenuImage::getImage).toList());
            return response;
        }

    }
