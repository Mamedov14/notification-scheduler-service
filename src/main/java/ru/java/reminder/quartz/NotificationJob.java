package ru.java.reminder.quartz;

import lombok.RequiredArgsConstructor;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import ru.java.reminder.service.NotificationService;

@RequiredArgsConstructor
public class NotificationJob implements Job {

    private final NotificationService notificationService;

    @Override
    public void execute(final JobExecutionContext context) {
        var notificationId = context.getJobDetail().getJobDataMap().getLong("notificationId");
        notificationService.processNotification(notificationId);
    }
}