package ru.java.reminder.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationListener {
    @RabbitListener(queues = "${messaging.consume.notification.queue}")
    public void receiveNotification(final String message) {
        log.info("Received notification: {}", message);
    }
}
