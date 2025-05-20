package cln.swiggy.payment.serviceImpl.OtherImpl;

import cln.swiggy.payment.exception.ResourceNotFoundException;
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


    public void validateOrderExists(Long orderId) {
        Boolean isOrderExists = (Boolean) rabbitTemplate.convertSendAndReceive(
                "payment_exchange",
                "payment_order_key",
                orderId
        );

        if (Boolean.FALSE.equals(isOrderExists)) {
            throw new ResourceNotFoundException("Order not found with id: " + orderId);
        }
    }
}
