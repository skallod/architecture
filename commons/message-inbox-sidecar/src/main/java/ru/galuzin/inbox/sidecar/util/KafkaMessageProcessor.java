package ru.galuzin.inbox.sidecar.util;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.listener.AcknowledgingMessageListener;
import org.springframework.kafka.listener.ConsumerSeekAware;
import org.springframework.kafka.support.Acknowledgment;
import ru.galuzin.inbox.sidecar.domain.Message;

import java.util.Map;
import java.util.Set;

public class KafkaMessageProcessor implements AcknowledgingMessageListener<String, Message>, ConsumerSeekAware {

    private static final Logger log = LoggerFactory.getLogger(KafkaMessageProcessor.class);

    @Override
    public void onMessage(ConsumerRecord<String, Message> data, Acknowledgment acknowledgment) {
        acknowledgment.acknowledge();
        log.info("on message {}, mes map = {}", data.headers(), data.value().toString());
    }

    @Override
    public void onPartitionsAssigned(Map<TopicPartition, Long> assignments, ConsumerSeekCallback callback) {
        assignments.keySet().forEach(tp->{
            log.info("seek to end {}, {}", tp.topic(), tp.partition());
            callback.seekToEnd(tp.topic(), tp.partition());
        });
    }
}
