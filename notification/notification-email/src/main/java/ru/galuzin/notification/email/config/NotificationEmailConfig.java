package ru.galuzin.notification.email.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.galuzin.notification.email.service.EmailSenderService;
import ru.galuzin.notification.mail.config.NotificationConfig;
import ru.galuzin.notification.mail.service.SenderService;

@Configuration
@Import({NotificationConfig.class})
public class NotificationEmailConfig {

    @Bean
    public SenderService senderService(){
        return new EmailSenderService();
    }
}
