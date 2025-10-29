package cln.swiggy.order.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(value = "orderRabbitMQConfig")
public class RabbitMQConfig {

    @Value("${rabbitmq.user.queue}")
    private String userQueue;

    @Value("${rabbitmq.user.exchange}")
    private String userExchange;

    @Value("${rabbitmq.user.routing-key}")
    private String userRoutingKey;

    @Value("${rabbitmq.category.queue}")
    private String categoryQueue;

    @Value("${rabbitmq.category.exchange}")
    private String categoryExchange;

    @Value("${rabbitmq.category.routing-key}")
    private String categoryRoutingKey;


    @Value("${rabbitmq.menu.queue}")
    private String menuQueue;

    @Value("${rabbitmq.menu.exchange}")
    private String menuExchange;

    @Value("${rabbitmq.menu.routing-key}")
    private String menuRoutingKey;

    @Value("${rabbitmq.restaurant.queue}")
    private String restaurantQueue;

    @Value("${rabbitmq.restaurant.exchange}")
    private String restaurantExchange;

    @Value("${rabbitmq.restaurant.routing-key}")
    private String restaurantRoutingKey;

    @Value("${rabbitmq.payment.queue}")
    private String paymentQueue;

    @Value("${rabbitmq.payment.exchange}")
    private String paymentExchange;

    @Value("${rabbitmq.payment.routing-key}")
    private String paymentRoutingKey;


    @Value("${rabbitmq.menuOrder.queue}")
    private String menuOrderQueue;

    @Value("${rabbitmq.menuOrder.exchange}")
    private String menuOrderExchange;

    @Value("${rabbitmq.menuOrder.routing-key}")
    private String menuOrderRoutingKey;


    @Value("${rabbitmq.saga.queue}")
    private String sagaQueue;

    @Value("${rabbitmq.saga.exchange}")
    private String sagaExchange;

    @Value("${rabbitmq.saga.routing-key}")
    private String sagaRoutingKey;


    @Bean
    public DirectExchange menuExchange() {
        return new DirectExchange(menuExchange);
    }

    @Bean
    public Queue menuQueue() {
        return new Queue(menuQueue);
    }

    @Bean
    public Binding Binding() {
        return BindingBuilder
                .bind(menuQueue())
                .to(menuExchange())
                .with(menuRoutingKey);
    }



    @Bean
    public DirectExchange userExchange() {
        return new DirectExchange(userExchange);
    }

    @Bean
    public Queue userQueue() {
        return new Queue(userQueue);
    }

    @Bean
    public Binding userBinding() {
        return BindingBuilder
                .bind(userQueue())
                .to(userExchange())
                .with(userRoutingKey);
    }



    @Bean
    public DirectExchange categoryExchange() {
        return new DirectExchange(categoryExchange);
    }

    @Bean
    public Queue categoryQueue() {
        return new Queue(categoryQueue);
    }

    @Bean
    public Binding categoryBinding() {
        return BindingBuilder
                .bind(categoryQueue())
                .to(categoryExchange())
                .with(categoryRoutingKey);
    }


    @Bean
    public DirectExchange restaurantExchange() {
        return new DirectExchange(restaurantExchange);
    }

    @Bean
    public Queue restaurantQueue() {
        return new Queue(restaurantQueue);
    }

    @Bean
    public Binding restaurantBinding() {
        return BindingBuilder
                .bind(restaurantQueue())
                .to(restaurantExchange())
                .with(restaurantRoutingKey);
    }

    @Bean
    public DirectExchange paymentExchange() {
        return new DirectExchange(paymentExchange);
    }

    @Bean
    public Queue paymentQueue() {
        return new Queue(paymentQueue , true);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder
                .bind(paymentQueue())
                .to(paymentExchange())
                .with(paymentRoutingKey);
    }


    @Bean
    public DirectExchange menuOrderExchange() {
        return new DirectExchange(menuOrderExchange);
    }

    @Bean
    public Queue menuOrderQueue() {
        return new Queue(menuOrderQueue , true);
    }

    @Bean
    public Binding menuOrderBinding() {
        return BindingBuilder
                .bind(menuOrderQueue())
                .to(menuOrderExchange())
                .with(menuOrderRoutingKey);
    }



    @Bean
    public DirectExchange sagaExchange() {
        return new DirectExchange(sagaExchange);
    }

    @Bean
    public Queue sagaQueue() {
        return new Queue(sagaQueue , true);
    }

    @Bean
    public Binding sagaBinding() {
        return BindingBuilder
                .bind(sagaQueue())
                .to(sagaExchange())
                .with(sagaRoutingKey);
    }


    @Bean(name = "orderJsonMessageConverter")
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }
}
