package cln.swiggy.user.consumer;

import cln.swiggy.user.repository.UserRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RatingConsumer {

    @Autowired
    UserRepository userRepository;

    @RabbitListener(queues = "is_user_available_for_rating")
    public Boolean consume(Long ownerId) {
        return userRepository.existsById(ownerId);
    }
}
