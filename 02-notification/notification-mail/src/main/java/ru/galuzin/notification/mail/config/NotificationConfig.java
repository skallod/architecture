package ru.galuzin.notification.mail.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.galuzin.notification.mail.service.NotificationInboxMessageService;
import ru.galuzin.inbox.sidecar.config.MessageHandlerConfig;
import ru.galuzin.inbox.sidecar.service.JsonConverterService;
import ru.galuzin.inbox.sidecar.service.MessageHandlerService;
import ru.galuzin.notification.mail.service.NotificationSenderSevice;
import ru.galuzin.notification.mail.service.OrderEventHandler;
import ru.galuzin.notification.mail.service.SenderService;

@Configuration
@Import(MessageHandlerConfig.class)
public class NotificationConfig {


//    @Configuration
//    @Profile("default")
//    @PropertySource("classpath:application.properties")
//    public static class Defaults
//    {
//    }

    @Bean
    public MessageHandlerService messageHandlerService(JsonConverterService jsonConverterService,
                                                       OrderEventHandler orderEventHandler) {
        return new NotificationInboxMessageService(jsonConverterService, orderEventHandler);
    }

    @Bean
    public OrderEventHandler orderEventHandler(@Value("${STORE_SERVICE_HTTP}")String storeService,
                                               RestTemplate restTemplate,
                                               SenderService senderService){
        return new OrderEventHandler(restTemplate,
                storeService,
                senderService
                );
    }

    @Bean
    public SenderService notificationSenderSevice(){
        return new NotificationSenderSevice();
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

}
