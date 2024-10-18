package ru.java.reminder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.java.reminder.entity.ScheduledNotification;

public interface NotificationRepository extends JpaRepository<ScheduledNotification, Long> {

}