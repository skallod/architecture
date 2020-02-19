package ru.galuzin.store.config;

import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.*;
import ru.galuzin.event.outbox.config.OutboxConfig;

@Configuration
@Import(OutboxConfig.class)
public class StoreConfig {
    @Configuration
    @Profile("default")
    @PropertySource("classpath:application.properties")
    public static class Defaults
    {
    }

    @Configuration
    @Profile("container")
    @PropertySource({"classpath:application-container.properties"})
    public static class Overrides
    {
    }

    @Bean
    public DozerBeanMapper dozerBeanMapper() throws Exception {
        return new DozerBeanMapper();
    }
}
