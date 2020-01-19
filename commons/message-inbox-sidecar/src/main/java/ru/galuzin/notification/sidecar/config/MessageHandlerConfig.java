package ru.galuzin.notification.sidecar.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import ru.galuzin.notification.sidecar.service.JsonConverterService;
import ru.galuzin.notification.sidecar.service.MessageHandlerService;
import ru.galuzin.notification.sidecar.service.MessageHandlerStub;
import ru.galuzin.notification.sidecar.service.Listener;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class MessageHandlerConfig {

    @Value("${kafka.broker.socket}")String brokerSoket;

//    @Bean
//    public NewTopic topic1() {
//        return TopicBuilder.name("thing1")
//                .partitions(10)
//                .replicas(3)
//                .compact()
//                .build();
//    }

    @Bean
    public MessageHandlerService eventHandlerService(){
        return new MessageHandlerStub();
    }

    @Bean
    ConcurrentKafkaListenerContainerFactory<Integer, String>
    kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<Integer, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setConcurrency(2);
        return factory;
    }

    @Bean
    public ConsumerFactory<Integer, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, brokerSoket);
//        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return props;
    }

    @Bean
    public Listener listener(MessageHandlerService messageHandlerService) {
        return new Listener(messageHandlerService, jsonConverterService());
    }

    @Bean
    public JsonConverterService jsonConverterService(){
        return new JsonConverterService();
    }
}
