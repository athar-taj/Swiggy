package cln.swiggy.rating.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.queue}")
    private String queue;

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.routing-key}")
    private String routingKey;


    @Bean
    public DirectExchange userExchange() {
        return new DirectExchange(exchange);
    }

    @Bean
    public Queue userAvailableQueue() {
        return QueueBuilder.durable(queue)
                .build();
    }

    @Bean
    public Binding userBinding() {
        return BindingBuilder
                .bind(userAvailableQueue())
                .to(userExchange())
                .with(routingKey);
    }
}
