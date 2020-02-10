package ru.galuzin.event.outbox.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import ru.galuzin.event.outbox.service.JsonConverterService;
import ru.galuzin.event.outbox.service.MessageOutboxService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EnableKafka
@Configuration
public class OutboxConfig {

    @Configuration
    @Profile("default")
    @PropertySource("classpath:outbox.properties")
    public static class Defaults
    {
    }

    @Configuration
    @Profile("container")
    @PropertySource({"classpath:outbox-container.properties"})
    public static class Overrides
    {
    }

    @Value("#{'${spring.kafka.bootstrap-servers}'.split(',')}")
    List<String> bootstrapServers = new ArrayList<>();

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        // See https://kafka.apache.org/documentation/#producerconfigs for more properties
        return props;
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<String, String>(producerFactory());
    }

    @Bean
    public JsonConverterService jsonConverterService(){
        return new JsonConverterService();
    }

    @Bean
    public MessageOutboxService sendService(){
        return new MessageOutboxService(kafkaTemplate(), jsonConverterService());
    }

}
