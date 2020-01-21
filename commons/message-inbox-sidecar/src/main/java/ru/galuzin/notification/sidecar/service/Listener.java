package ru.galuzin.notification.sidecar.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import ru.galuzin.notification.sidecar.domain.Message;

public class Listener {

    private final static Logger log = LoggerFactory.getLogger(Listener.class);

    private final MessageHandlerService messageHandlerService;

    private final JsonConverterService jsonConverterService;

    public Listener(MessageHandlerService messageHandlerService,
                    JsonConverterService jsonConverterService) {
        this.messageHandlerService = messageHandlerService;
        this.jsonConverterService = jsonConverterService;
    }

    //@KafkaListener(groupId = "group_3", topics = {"${topic.name}"})
    @KafkaListener(groupId = "group_3", topics = "#{'${listen.topic.name}'.split(',')}")
    public void onMessage(String messageStr) {
        log.info("inbox mesage = " + messageStr);
        try {
            Message message = jsonConverterService.fromJson(messageStr, Message.class);
            messageHandlerService.hadleMessage(message);
        } catch (Exception e) {
            log.error("handle message fail", e);
        }
    }
}
