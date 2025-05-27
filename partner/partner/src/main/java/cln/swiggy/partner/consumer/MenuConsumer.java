package cln.swiggy.partner.consumer;

import cln.swiggy.partner.model.Menu;
import cln.swiggy.partner.repository.MenuRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
public class MenuConsumer {

    @Autowired
    MenuRepository menuRepository;

    @RabbitListener(queues = "is_menu_exists_for_order")
    public Boolean isMenuExistsForOrder(Long menuId) {
        return menuRepository.existsById(menuId);
    }

    @RabbitListener(queues = "menu_price_for_order")
    public HashMap<String,Object> getMenuPriceForOrder(Long menuId) {
        HashMap<String,Object> menu = new HashMap<>();
        Optional<Menu> menus = menuRepository.findById(menuId);

        menu.put("Price",menus.map(Menu::getPrice).orElse(null));
        menu.put("Category",menus.get().getCategory().getName());

        return menu;
    }

    @RabbitListener(queues = "update_menu_rating")
    public Boolean updateMenuRating(HashMap<String, Object> data) {

        Optional<Menu> menu = menuRepository.findById(Long.valueOf(data.get("menuId").toString()));
        if (menu.isPresent()) {
            Menu rest = menu.get();

            rest.setTotalRating(rest.getTotalRating() + 1);


            double newRatingValue = Double.parseDouble(data.get("rating").toString());
            double currentAvgRating = rest.getRating();
            double newAvgRating = ((currentAvgRating * (rest.getTotalRating() - 1)) + newRatingValue) / rest.getTotalRating();

            newAvgRating = Math.round(newAvgRating * 10.0) / 10.0;

            rest.setRating(newAvgRating);
            menuRepository.save(rest);
            return true;
        }
        return false;
    }

    @RabbitListener(queues = "update_menu_order_count")
    public Boolean updateMenuOrderCount(String menuId) {
        System.out.println(menuId);
        Optional<Menu> menu = menuRepository.findById(Long.valueOf(menuId));
        menu.get().setTotalOrders(menu.get().getTotalOrders() + 1);
        menuRepository.save(menu.get());
        return true;
    }
}
