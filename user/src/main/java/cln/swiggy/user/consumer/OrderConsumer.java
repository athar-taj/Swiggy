package cln.swiggy.user.consumer;

import cln.swiggy.user.repository.UserRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderConsumer {

    @Autowired
    UserRepository userRepository;

    @RabbitListener(queues = "${rabbitmq.queue.order}")
    public Boolean consume(Long ownerId) {
        return userRepository.existsById(ownerId);
    }

}