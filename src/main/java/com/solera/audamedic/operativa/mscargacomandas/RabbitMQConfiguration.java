package com.solera.audamedic.operativa.mscargacomandas;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class RabbitMQConfiguration {

    @Value("${rabbitmq.exchange.cargafinalizada}")
    String exchangeName;

    @Value("${rabbitmq.queue.cargafinalizada}")
    String queueName;

    @Value("${rabbitmq.queue.cargafinalizada.dlq}")
    String dlqQueueName;

    @Value("${rabbitmq.exchange.cargafinalizada.dlx}")
    String dlxExchangeName;
    @Bean
    FanoutExchange exchange() {
        return new FanoutExchange(exchangeName);
    }

    @Bean
    FanoutExchange deadLetterExchange() {
        return new FanoutExchange(dlxExchangeName);
    }

    @Bean
    public Queue queue() {
        return QueueBuilder.durable(queueName)
                .withArgument("x-dead-letter-exchange", dlxExchangeName)
                .withArgument("x-dead-letter-routing-key", dlqQueueName)
                .build();
    }

    @Bean
    public Queue deadLetterQueue() {
        return new Queue(dlqQueueName, true);
    }

    @Bean
    Binding binding(Queue queue, FanoutExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange);
    }

    @Bean
    Binding dlqBinding(Queue deadLetterQueue, @Qualifier("deadLetterExchange") FanoutExchange deadLetterExchange) {
        return BindingBuilder.bind(deadLetterQueue).to(deadLetterExchange);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}
