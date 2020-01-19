package ru.galuzin.event.outbox.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import ru.galuzin.event.outbox.domain.Message;

import java.util.concurrent.TimeUnit;

public class SendService {

    private static final Logger log = LoggerFactory.getLogger(SendService.class);

    private final KafkaTemplate<String,String> kafkaTemplate;

    final private JsonConverterService jsonConverterService;

    public SendService(KafkaTemplate<String, String> kafkaTemplate,
                       JsonConverterService jsonConverterService) {
        this.kafkaTemplate = kafkaTemplate;
        this.jsonConverterService = jsonConverterService;
    }

    public void send(String topik, Message message){
        try {
            String messageStr = jsonConverterService.toJson(message);
            log.info("outbox message {} {}", message.getId(), messageStr);
            ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topik, message.getId(), messageStr);
            future.get(10, TimeUnit.SECONDS);
            log.info("value sended {}", message.getId());
        } catch (Exception e) {
            log.error("outbox send fail", e);
        }
    }
}
