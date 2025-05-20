package cln.swiggy.restaurant.consumer;

import cln.swiggy.restaurant.repository.MenuRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MenuConsumer {

    @Autowired
    MenuRepository menuRepository;

    @RabbitListener(queues = "is_menu_exists_for_order")
    public Boolean isMenuExistsForOrder(Long menuId) {
        return menuRepository.existsById(menuId);
    }

    @RabbitListener(queues = "menu_price_for_order")
    public Double getMenuPriceForOrder(Long menuId) {
        return menuRepository.findById(menuId)
                .orElseThrow(() -> new IllegalArgumentException("Menu not found with id: " + menuId))
                .getPrice();
    }
}
