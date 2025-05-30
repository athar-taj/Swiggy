package cln.swiggy.notification.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.user-notification.exchange}")
    private String userExchange;

    @Value("${rabbitmq.restaurant-notification.exchange}")
    private String restaurantExchange;

    // User queues
    @Value("${rabbitmq.user-notification.queues.booking}")
    private String userBookingQueue;
    @Value("${rabbitmq.user-notification.queues.offer}")
    private String userOfferQueue;
    @Value("${rabbitmq.user-notification.queues.itemCombo}")
    private String userItemComboQueue;

    @Value("${rabbitmq.user-notification.routing-keys.booking}")
    private String userBookingRoutingKey;
    @Value("${rabbitmq.user-notification.routing-keys.offer}")
    private String userOfferRoutingKey;
    @Value("${rabbitmq.user-notification.routing-keys.itemCombo}")
    private String userItemComboRoutingKey;

    // Restaurant queues
    @Value("${rabbitmq.restaurant-notification.queues.order}")
    private String restOrderQueue;
    @Value("${rabbitmq.restaurant-notification.queues.booking}")
    private String restBookingQueue;
    @Value("${rabbitmq.restaurant-notification.queues.payment}")
    private String restPaymentQueue;
    @Value("${rabbitmq.restaurant-notification.queues.rating}")
    private String restRatingQueue;

    @Value("${rabbitmq.restaurant-notification.routing-keys.order}")
    private String restOrderRoutingKey;
    @Value("${rabbitmq.restaurant-notification.routing-keys.booking}")
    private String restBookingRoutingKey;
    @Value("${rabbitmq.restaurant-notification.routing-keys.payment}")
    private String restPaymentRoutingKey;
    @Value("${rabbitmq.restaurant-notification.routing-keys.rating}")
    private String restRatingRoutingKey;


    @Bean
    public TopicExchange userNotificationExchange() {
        System.out.println("User Executing " + userExchange);
        return new TopicExchange(userExchange);
    }

    @Bean
    public TopicExchange restaurantNotificationExchange() {
        System.out.println("Restaurant Executing " + restaurantExchange);
        return new TopicExchange(restaurantExchange);
    }

    // USER


    @Bean public Queue userBookingQueueBean() { return new Queue(userBookingQueue); }
    @Bean public Binding userBookingBinding() {
        return BindingBuilder.bind(userBookingQueueBean()).to(userNotificationExchange()).with(userBookingRoutingKey);
    }

    @Bean public Queue userOfferQueueBean() { return new Queue(userOfferQueue); }
    @Bean public Binding userOfferBinding() {
        return BindingBuilder.bind(userOfferQueueBean()).to(userNotificationExchange()).with(userOfferRoutingKey);
    }

    @Bean public Queue userItemComboQueueBean() { return new Queue(userItemComboQueue); }
    @Bean public Binding userItemComboBinding() {
        return BindingBuilder.bind(userItemComboQueueBean()).to(userNotificationExchange()).with(userItemComboRoutingKey);
    }

// RESTAURANT

    @Bean public Queue restOrderQueueBean() { return new Queue(restOrderQueue); }
    @Bean public Binding restOrderBinding() {
        return BindingBuilder.bind(restOrderQueueBean()).to(restaurantNotificationExchange()).with(restOrderRoutingKey);
    }

    @Bean public Queue restBookingQueueBean() { return new Queue(restBookingQueue); }
    @Bean public Binding restBookingBinding() {
        return BindingBuilder.bind(restBookingQueueBean()).to(restaurantNotificationExchange()).with(restBookingRoutingKey);
    }

    @Bean public Queue restPaymentQueueBean() { return new Queue(restPaymentQueue); }
    @Bean public Binding restPaymentBinding() {
        return BindingBuilder.bind(restPaymentQueueBean()).to(restaurantNotificationExchange()).with(restPaymentRoutingKey);
    }

    @Bean public Queue restRatingQueueBean() { return new Queue(restRatingQueue); }
    @Bean public Binding restRatingBinding() {
        return BindingBuilder.bind(restRatingQueueBean()).to(restaurantNotificationExchange()).with(restRatingRoutingKey);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper();
        typeMapper.setTrustedPackages("*");
        converter.setJavaTypeMapper(typeMapper);
        return converter;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter jsonMessageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter);
        return rabbitTemplate;
    }
}
