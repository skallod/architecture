package ru.galuzin.notification.sms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.galuzin.notification.mail.config.NotificationConfig;
import ru.galuzin.notification.mail.service.SenderService;
import ru.galuzin.notification.sms.service.NotificationSmsSenderService;

@Configuration
@Import({NotificationConfig.class})
public class NotificationSmsConfig {

    @Bean
    public SenderService notificationSenderSevice(){
        return new NotificationSmsSenderService();
    }
}
