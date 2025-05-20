package cln.swiggy.order.serviceImpl.otherImpl;

import cln.swiggy.order.exception.ResourceNotFoundException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EntityValidator {
    @Autowired
    RabbitTemplate rabbitTemplate;

    public void validateUserExists(Long userId) {
        Boolean isUserExists = (Boolean) rabbitTemplate.convertSendAndReceive(
            "user-exchange", 
            "user_order_key",
            userId
        );
        
        if (Boolean.FALSE.equals(isUserExists)) {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }
    }

    public void validateMenuExists(Long menuId) {
        Boolean isMenuExists = (Boolean) rabbitTemplate.convertSendAndReceive(
            "menu-exchange",
            "menu_order_key",
            menuId
        );
        
        if (Boolean.FALSE.equals(isMenuExists)) {
            throw new ResourceNotFoundException("Menu not found with id: " + menuId);
        }
    }

    public void validateRestaurantExists(Long restaurantId) {
        Boolean isRestaurantExists = (Boolean) rabbitTemplate.convertSendAndReceive(
            "restaurant-exchange",
            "restaurant_order_key",
            restaurantId
        );
        
        if (Boolean.FALSE.equals(isRestaurantExists)) {
            throw new ResourceNotFoundException("Restaurant not found with id: " + restaurantId);
        }
    }
}