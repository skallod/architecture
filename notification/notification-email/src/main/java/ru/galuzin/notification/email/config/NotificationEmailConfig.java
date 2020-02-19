package ru.galuzin.notification.email.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.galuzin.notification.email.service.EmailSenderService;
import ru.galuzin.notification.mail.service.SenderService;

@Configuration
public class NotificationEmailConfig {

    @Bean
    public SenderService senderService(){
        return new EmailSenderService();
    }
}
