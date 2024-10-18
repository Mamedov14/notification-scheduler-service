package ru.java.reminder.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ru.java.reminder.configuration.RabbitMQProperties;

@Service
@RequiredArgsConstructor
public class NotificationProducer {

    private final RabbitTemplate rabbitTemplate;
    private final RabbitMQProperties properties;

    public void sendNotification(final String message) {
        rabbitTemplate.convertAndSend(
                properties.getProduce().getNotification().getExchange(),
                properties.getProduce().getNotification().getRoutingKey(),
                message
        );
    }
}
