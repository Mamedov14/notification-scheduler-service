package ru.java.reminder.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ru.java.reminder.configuration.RabbitMQProperties;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationListener {
    @RabbitListener(queues = "${messaging.consume.notification.queue}")
    public void receiveNotification(String message) {
        log.info("Received notification: {}", message);
    }
}
