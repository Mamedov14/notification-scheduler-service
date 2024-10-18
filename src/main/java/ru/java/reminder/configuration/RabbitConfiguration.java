package ru.java.reminder.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Declarable;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(RabbitMQProperties.class)
public class RabbitConfiguration {

    private final RabbitMQProperties properties;

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory,
                                         final Jackson2JsonMessageConverter jsonMessageConverter) {
        var template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter);
        return template;
    }

    @Bean
    public Declarables declarables() {
        var declarables = new ArrayList<Declarable>();
        var consume = properties.getConsume();
        var produce = properties.getProduce();

        declarables.add(declarableExchange(consume.getExchange()));
        declarables.add(declarableQueue(consume.getNotification().getQueue()));

        declarables.add(declarableExchange(produce.getNotification().getExchange()));

        declarables.add(new Binding(
                consume.getNotification().getQueue(),
                Binding.DestinationType.QUEUE,
                produce.getNotification().getExchange(),
                produce.getNotification().getRoutingKey(),
                null
        ));

        return new Declarables(declarables);
    }

    private Exchange declarableExchange(final String name) {
        return new TopicExchange(name, true, false);
    }

    private Queue declarableQueue(final String name) {
        return new Queue(name, true);
    }
}
