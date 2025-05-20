package cln.swiggy.user.consumer;

import cln.swiggy.user.model.User;
import cln.swiggy.user.repository.UserRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RestaurantConsumer {

    @Autowired
    UserRepository userRepository;

    @RabbitListener(queues = "${rabbitmq.queue.restaurant}", containerFactory = "rabbitListenerContainerFactory")
    public Boolean consume(String ownerId) {
        boolean exists = userRepository.existsById(Long.valueOf(ownerId));
        System.out.println("Returning: " + exists);
        return exists;
    }

    @RabbitListener(queues = "${rabbitmq.queue.user.email}", containerFactory = "rabbitListenerContainerFactory")
    public String consumeEmail(Long ownerId) {
        System.out.println("Email Returning: " + ownerId);
        Optional<User> user = userRepository.findById(ownerId);
        System.out.println(user.get().getEmail());
        return user.get().getEmail();
    }

}
