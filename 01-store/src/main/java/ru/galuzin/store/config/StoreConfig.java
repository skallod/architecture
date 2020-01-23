package ru.galuzin.store.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
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
}
