package ru.java.reminder.service;


import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobBuilder;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.springframework.stereotype.Service;
import ru.java.reminder.dto.NotificationRequest;
import ru.java.reminder.entity.ScheduledNotification;
import ru.java.reminder.quartz.NotificationJob;
import ru.java.reminder.repository.NotificationRepository;

import java.time.ZoneId;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final Scheduler scheduler;
    private final NotificationRepository notificationRepository;

    @SneakyThrows
    public void scheduleNotification(NotificationRequest request) {
        var notification = ScheduledNotification.builder()
                .message(request.getMessage())
                .notificationTime(request.getNotificationTime())
                .isSent(false)
                .build();
        // todo как гарантировать 100% отправку?
        notificationRepository.save(notification);
        scheduleJob(notification);
    }

    private void scheduleJob(final ScheduledNotification notification) throws SchedulerException {
        var jobDetail = JobBuilder.newJob(NotificationJob.class)
                .withIdentity("notificationJob" + notification.getId(), "notifications")
                .usingJobData("notificationId", notification.getId())
                .build();
        var trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger" + notification.getId(), "notifications")
                .startAt(Date.from(notification.getNotificationTime().atZone(ZoneId.systemDefault()).toInstant()))
                .build();
        scheduler.scheduleJob(jobDetail, trigger);
    }

    public void processNotification(final Long notificationId) {
        notificationRepository.findById(notificationId)
                .ifPresent(notification -> {
                    sendNotification(notification.getMessage());
                    notification.setIsSent(true);
                    notificationRepository.save(notification);
                });
    }

    private void sendNotification(final String message) {
        log.info("Notification sent: {}", message);
    }
}