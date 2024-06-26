package it.unibz.bulletify.querier.app;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RabbitMqConfig {
    static final String topicExchange = "bulletify-exchange";
    static final String queueName = "items";

    @Bean
    Queue itemsQueue() {
        return QueueBuilder.durable(queueName).build();
    }

    @Bean
    FanoutExchange bulletifyExchange() {
        return new FanoutExchange(topicExchange);
    }

    @Bean
    Binding binding(Queue queue, FanoutExchange exchange) {
        return BindingBuilder
                .bind(queue)
                .to(exchange);
    }

    @Bean
    MessageListenerAdapter listenerAdapter(RecordItems receiver) {
        return new MessageListenerAdapter(
                receiver,
                "recordItemCreated"
        );
    }

    @Bean
    SimpleMessageListenerContainer container(
            ConnectionFactory connectionFactory,
            MessageListenerAdapter listenerAdapter
    ) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName);
        container.setMessageListener(listenerAdapter);
        return container;
    }
}
