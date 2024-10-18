package ru.java.reminder.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.java.reminder.dto.NotificationRequest;
import ru.java.reminder.service.NotificationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("/schedule")
    public ResponseEntity<String> scheduleNotification(@RequestBody NotificationRequest request) {
        notificationService.scheduleNotification(request);
        return ResponseEntity.ok("Notification scheduled successfully");
    }
}
