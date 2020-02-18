package ru.galuzin.inbox.sidecar.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.listener.MessageListener;
import ru.galuzin.inbox.sidecar.util.KafkaContainerHolder;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class KafkaContainerConfig {

    @Value("#{'${spring.kafka.bootstrap-servers}'.split(',')}")
    List<String> bootstrapServers = new ArrayList<>();

    @Bean
    public KafkaContainerHolder kafkaContainerHolder(
    ){
        return new KafkaContainerHolder(bootstrapServers);
    }
}
