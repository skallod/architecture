package ru.galuzin.notification.mail.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.galuzin.notification.mail.service.NotificationInboxMessageService;
import ru.galuzin.notification.sidecar.config.MessageHandlerConfig;
import ru.galuzin.notification.sidecar.service.JsonConverterService;
import ru.galuzin.notification.sidecar.service.MessageHandlerService;

@Configuration
@Import(MessageHandlerConfig.class)
public class NotificationConfig {
    @Bean
    public MessageHandlerService messageHandlerService(JsonConverterService jsonConverterService){
        return new NotificationInboxMessageService(jsonConverterService);
    }

}
