package cln.swiggy.order.consumer;

import cln.swiggy.order.repository.OrderRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class paymentConsumer {

    @Autowired
    OrderRepository orderRepository;

    @RabbitListener(queues = "is_order_exists_for_payment")
    public Boolean consume(Long orderId){
        return orderRepository.existsById(orderId);
    }
}
