package ru.galuzin.notification.sidecar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class InboxSidecarApp {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(InboxSidecarApp.class, args);
    }
}
