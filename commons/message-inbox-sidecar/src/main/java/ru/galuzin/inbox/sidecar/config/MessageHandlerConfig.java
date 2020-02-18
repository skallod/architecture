package ru.galuzin.inbox.sidecar.config;

import org.apache.kafka.clients.admin.*;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.Metric;
import org.apache.kafka.common.MetricName;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.TopicPartitionReplica;
import org.apache.kafka.common.acl.AclBinding;
import org.apache.kafka.common.acl.AclBindingFilter;
import org.apache.kafka.common.config.ConfigResource;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import ru.galuzin.inbox.sidecar.service.JsonConverterService;
import ru.galuzin.inbox.sidecar.service.MessageHandlerService;
import ru.galuzin.inbox.sidecar.service.MessageHandlerStub;
import ru.galuzin.inbox.sidecar.service.Listener;
import ru.galuzin.inbox.sidecar.util.KafkaContainerHolder;

import java.time.Duration;
import java.util.*;

//@Configuration
//@EnableKafka
public class MessageHandlerConfig {

    private static final Logger log = LoggerFactory.getLogger(MessageHandlerConfig.class);

    @Configuration
    @Profile("default")
    @PropertySource("classpath:inbox.properties")
    public static class Defaults
    {
    }

    @Configuration
    @Profile("container")
    @PropertySource({"classpath:inbox-container.properties"})
    public static class Overrides
    {
    }

//    @Bean
//    public DynamicBeanFactoryProcessor dynamicBeanFactoryProcessor(
//            @Value("#{'${listen.topic.name}'.split(',')}") List<String> topics
//    ){
//        log.info("dynamic topics {}", topics);
//        return new DynamicBeanFactoryProcessor(topics);
//    }

    //    @Bean
//    public NewTopic topic1() {
//        return TopicBuilder.name("thing1")
//                .partitions(10)
//                .replicas(3)
//                .compact()
//                .build();
//    }

//    @Bean
//    public KafkaAdmin kafkaAdmin(@Value("${broker.socket}")String brokerSocket,
//                                 @Value("${listen.topic.name}")String topics,
//                                 KafkaProperties kafkaProperties) {
//        Map<String, Object> adminProperties = kafkaProperties.buildAdminProperties();
//        log.info("message inbox init3 {}, {}, {}", brokerSocket, topics,
//                kafkaProperties.getBootstrapServers());
//        Map<String, Object> configs = new HashMap<>();
//        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, brokerSocket);
//        return new KafkaAdmin(adminProperties);
//    }

    @Value("#{'${spring.kafka.bootstrap-servers}'.split(',')}")
    List<String> bootstrapServers = new ArrayList<>();

    @Value("#{'${listen.topic.name}'.split(',')}")
    List<String> topics;

    @Bean
    public MessageHandlerService eventHandlerService(){
        return new MessageHandlerStub();
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory(
            /*MessageListener<String, String>*/ Listener kafkaListener
    ) {
        //new ConcurrentKafkaListenerContainer();
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setConcurrency(3);
        factory.getContainerProperties().setPollTimeout(60_000);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        factory.getContainerProperties().setMissingTopicsFatal(false);
        factory.getContainerProperties().setMessageListener(kafkaListener);
//        factory.getContainerProperties().setAckTime(10_000);
//        factory.getContainerProperties().setAckCount(1);
        return factory;
    }

    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    @Bean
    public Map<String, Object> consumerConfigs() {
        log.info("consumer factory broker {}", bootstrapServers);
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return props;
    }


    @Bean
    public Listener kafkaListener(MessageHandlerService messageHandlerService) {
        return new Listener(messageHandlerService, jsonConverterService());
    }

    @Bean
    public JsonConverterService jsonConverterService(){
        return new JsonConverterService();
    }


}
