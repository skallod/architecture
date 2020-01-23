package ru.galuzin.notification.mail.config;

import org.springframework.context.annotation.*;
import ru.galuzin.notification.mail.service.NotificationInboxMessageService;
import ru.galuzin.inbox.sidecar.config.MessageHandlerConfig;
import ru.galuzin.inbox.sidecar.service.JsonConverterService;
import ru.galuzin.inbox.sidecar.service.MessageHandlerService;
import ru.galuzin.notification.mail.service.OrderEventHandler;

@Configuration
@Import(MessageHandlerConfig.class)
public class NotificationConfig {


    @Configuration
    @Profile("default")
    @PropertySource("classpath:application.properties")
    public static class Defaults
    {
    }

    @Bean
    public MessageHandlerService messageHandlerService(JsonConverterService jsonConverterService,
                                                       OrderEventHandler orderEventHandler){
        return new NotificationInboxMessageService(jsonConverterService, orderEventHandler);
    }

}
