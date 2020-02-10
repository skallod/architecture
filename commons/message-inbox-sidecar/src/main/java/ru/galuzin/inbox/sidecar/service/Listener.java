package ru.galuzin.inbox.sidecar.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.AcknowledgingMessageListener;
import org.springframework.kafka.support.Acknowledgment;
import ru.galuzin.inbox.sidecar.domain.Message;

public class Listener /*implements AcknowledgingMessageListener<String, String>*/ {

    private final static Logger log = LoggerFactory.getLogger(Listener.class);

    private final MessageHandlerService messageHandlerService;

    private final JsonConverterService jsonConverterService;

    public Listener(MessageHandlerService messageHandlerService,
                    JsonConverterService jsonConverterService) {
        this.messageHandlerService = messageHandlerService;
        this.jsonConverterService = jsonConverterService;
    }

//    @Override
//    public void onMessage(ConsumerRecord<String, String> data, Acknowledgment acknowledgment) {
//        log.info("inbox topic {}, mesage = {}", data.topic(), data.value());
//        try {
//            Message message = jsonConverterService.fromJson(data.value(), Message.class);
//            messageHandlerService.hadleMessage(message);
//        } catch (Exception e) {
//            log.error("handle message fail", e);
//        } finally {
//            acknowledgment.acknowledge();
//        }
//    }

    //@KafkaListener(groupId = "group_3", topics = {"${topic.name}"})
    @KafkaListener(groupId = "${NODE_ID}",topics = "#{'${listen.topic.name}'.split(',')}")
    public void onMessage(String messageStr, Acknowledgment acknowledgment) {
        log.info("inbox mesage = " + messageStr);
        try {
            Message message = jsonConverterService.fromJson(messageStr, Message.class);
            messageHandlerService.hadleMessage(message);
        } catch (Exception e) {
            log.error("handle message fail", e);
        } finally {
            acknowledgment.acknowledge();
        }
    }
}
