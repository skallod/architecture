package ru.galuzin.inbox.sidecar.util;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.RoundRobinAssignor;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.*;
import org.springframework.kafka.listener.adapter.RetryingMessageListenerAdapter;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.NeverRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import ru.galuzin.inbox.sidecar.domain.Message;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KafkaContainerHolder {

    private static final Logger log = LoggerFactory.getLogger(KafkaContainerHolder.class);

    List<ConcurrentMessageListenerContainer> containers = new ArrayList<>();

    final List<String> bootstrapServers;

    public KafkaContainerHolder(List<String> bootstrapServers) {
        this.bootstrapServers = bootstrapServers;
    }

    @PostConstruct
    public void init(){
        log.info("container holder init");
        String groupId = "houlder_group_1";
        String topic = "orders-topic";
        int concurrency = 2;
        ContainerProperties containerProps = new ContainerProperties(topic);
        containerProps.setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        RetryTemplate retryTemplate = new RetryTemplate();
        retryTemplate.setRetryPolicy(new NeverRetryPolicy());
        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        retryTemplate.setBackOffPolicy(backOffPolicy);
        AcknowledgingMessageListener<String,Message> processor = new AcknowledgingMessageListener<String,Message>() {
            @Override
            public void onMessage(ConsumerRecord data, Acknowledgment acknowledgment) {
                acknowledgment.acknowledge();
                log.info("on message {}, mes map = {}", data.headers(), data.value().toString());
            }
        };
//        MessageListener<String,Message> processor = new MessageListener<String,Message>() {
//            @Override
//            public void onMessage(ConsumerRecord<String, Message> data) {
//                log.info("on message {}, mes map = {}", data.headers(), data.value().toString());
//            }
//        };
        RetryingMessageListenerAdapter<String, Message> l
                = new RetryingMessageListenerAdapter<>(processor, retryTemplate);
        containerProps.setMessageListener(l);

        HashMap<String, Object> factoryProps = new HashMap<>();
        factoryProps.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        factoryProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        factoryProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        factoryProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        factoryProps.putAll(getFactoryProps());
        factoryProps.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        factoryProps.put(ConsumerConfig.CLIENT_ID_CONFIG, "holder_client_1");

        DefaultKafkaConsumerFactory<String, Message> cf = new DefaultKafkaConsumerFactory<>(factoryProps
                , new StringDeserializer(), new JsonDeserializer<>(Message.class));
        ConcurrentMessageListenerContainer<String, Message> container
                = new ConcurrentMessageListenerContainer<String, Message>(cf, containerProps);
        container.setBeanName("container_bean_1");
        container.setConcurrency(concurrency);
        container.start();
        containers.add(container);
    }

    Map<String, Object> getFactoryProps(){
        Map<String, Object> map = new HashMap<>();
        map.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        map.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 5000);
        map.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");//"latest");//"earliest");
        map.put(ConsumerConfig.CHECK_CRCS_CONFIG, true);
        map.put(ConsumerConfig.CONNECTIONS_MAX_IDLE_MS_CONFIG, 540_000);
        map.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);//false);
        map.put(ConsumerConfig.FETCH_MAX_BYTES_CONFIG, 52_428_800);
        map.put(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG, 1500);
        map.put(ConsumerConfig.FETCH_MIN_BYTES_CONFIG, 500);
        map.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, 3000);
        map.put(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG, 1_048_576);
        map.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 300_000);
        map.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG,10);
        map.put(ConsumerConfig.METADATA_MAX_AGE_CONFIG,90_000);
        map.put(ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG, "org.apache.kafka.clients.consumer.RoundRobinAssignor");
        map.put(ConsumerConfig.RECEIVE_BUFFER_CONFIG, 1024_000);
        map.put(ConsumerConfig.RECONNECT_BACKOFF_MS_CONFIG, 50);
        map.put(ConsumerConfig.REQUEST_TIMEOUT_MS_CONFIG, 40_000);
        map.put(ConsumerConfig.RETRY_BACKOFF_MS_CONFIG, 100);
        return map;
    }

    @PreDestroy
    public void destroy(){
        containers.forEach(c->c.stop());
    }
}
