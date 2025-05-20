package cln.swiggy.restaurant.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(value = "restaurantRabbitMQConfig")
public class RabbitMQConfig {

    @Value("${rabbitmq.queue}")
    private String queue;

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.routing-key}")
    private String routingKey;


    @Value("${rabbitmq.user.queue}")
    private String userQueue;

    @Value("${rabbitmq.user.exchange}")
    private String userExchange;

    @Value("${rabbitmq.user.routing-key}")
    private String userRoutingKey;

    @Value("${rabbitmq.menu.queue}")
    private String menuQueue;

    @Value("${rabbitmq.menu.exchange}")
    private String menuExchange;

    @Value("${rabbitmq.menu.routing-key}")
    private String menuRoutingKey;



    @Bean
    public DirectExchange restaurantExchange() {
        return new DirectExchange(exchange);
    }

    @Bean
    public Queue restaurantAvailableQueue() {
        return QueueBuilder.durable(queue)
                .build();
    }

    @Bean
    public Binding restaurantBinding() {
        return BindingBuilder
                .bind(restaurantAvailableQueue())
                .to(restaurantExchange())
                .with(routingKey);
    }


    @Bean
    public DirectExchange userExchange() {
        return new DirectExchange(userExchange);
    }

    @Bean
    public Queue userAvailableQueue() {
        return QueueBuilder.durable(userQueue)
                .build();
    }

    @Bean
    public Binding userBinding() {
        return BindingBuilder
                .bind(userAvailableQueue())
                .to(userExchange())
                .with(userRoutingKey);
    }


    @Bean
    public DirectExchange menuExchange() {
        return new DirectExchange(menuExchange);
    }

    @Bean
    public Queue menuAvailableQueue() {
        return QueueBuilder.durable(menuQueue)
                .build();
    }

    @Bean
    public Binding menuBinding() {
        return BindingBuilder
                .bind(menuAvailableQueue())
                .to(menuExchange())
                .with(menuRoutingKey);
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

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory,
            MessageConverter jsonMessageConverter
    ) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jsonMessageConverter);
        return factory;
    }


    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin admin = new RabbitAdmin(connectionFactory);
        admin.setAutoStartup(true);
        return admin;
    }

}
