package cln.swiggy.order.consumer;

import cln.swiggy.order.model.Order;
import cln.swiggy.order.model.SagaEventEntity;
import cln.swiggy.order.model.enums.OrderStatus;
import cln.swiggy.order.repository.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class OrderConsumer {

    @Autowired
    OrderRepository orderRepository;

    @RabbitListener(queues = "is_order_exists_for_payment")
    public Boolean consume(Long orderId){
        return orderRepository.existsById(orderId);
    }

    @RabbitListener(queues = "saga_event_queue")
    public void sagaConsumer(String eventJson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            SagaEventEntity event = objectMapper.readValue(eventJson, SagaEventEntity.class);

            if (event.getOrderId() != null) {
                Optional<Order> optionalOrder = orderRepository.findById(event.getOrderId());

                if (optionalOrder.isPresent()) {
                    Order order = optionalOrder.get();

                    switch (event.getEventType()) {
                        case INVENTORY_RESERVATION_FAILED:
                        case PAYMENT_FAILED:
                        case ORDER_FAILED:
                            order.setOrderStatus(OrderStatus.FAILED);
                            break;

                        case PAYMENT_SUCCESS:
                            order.setOrderStatus(OrderStatus.PLACED);
                            break;

                        case INVENTORY_RESERVED:
                            order.setOrderStatus(OrderStatus.PENDING);
                            break;

                        default:
                            break;
                    }
                    orderRepository.save(order);
                } else {
                    System.err.println("Order not found for Saga event: " + event.getOrderId());
                }
            } else {
                System.err.println("No OrderId in SagaEvent: " + event.getEventId());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
