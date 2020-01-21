package ru.galuzin.notification.sidecar.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import ru.galuzin.notification.sidecar.service.JsonConverterService;
import ru.galuzin.notification.sidecar.service.MessageHandlerService;
import ru.galuzin.notification.sidecar.service.MessageHandlerStub;
import ru.galuzin.notification.sidecar.service.Listener;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class MessageHandlerConfig {

    @Bean
    public DynamicBeanFactoryProcessor dynamicBeanFactoryProcessor(){
        return new DynamicBeanFactoryProcessor();
    }
//    @Bean
//    public NewTopic topic1() {
//        return TopicBuilder.name("thing1")
//                .partitions(10)
//                .replicas(3)
//                .compact()
//                .build();
//    }

    @Bean
    public KafkaAdmin kafkaAdmin(@Value("${broker.socket}")String brokerSocket) {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, brokerSocket);
        KafkaAdmin kafkaAdmin = new KafkaAdmin(configs);
        return kafkaAdmin;
    }

    @Bean
    public MessageHandlerService eventHandlerService(){
        return new MessageHandlerStub();
    }

    @Bean
    ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory(
            @Value("${broker.socket}")String brokerSocket
    ) {
        //new ConcurrentKafkaListenerContainer();
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory(brokerSocket));
        //factory.setConcurrency(2);
        return factory;
    }

    @Bean
    public ConsumerFactory<String, String> consumerFactory(String brokerSocket) {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, brokerSocket);
//        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props);
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
