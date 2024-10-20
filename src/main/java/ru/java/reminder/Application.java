package ru.java.reminder;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class Application {
    public static void main(final String[] args) {
        log.info("Application started");
        SpringApplication.run(Application.class, args);
    }
}
