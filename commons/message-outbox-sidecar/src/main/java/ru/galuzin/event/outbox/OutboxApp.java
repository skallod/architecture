package ru.galuzin.event.outbox;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.PropertySource;
import ru.galuzin.event.outbox.domain.Message;
import ru.galuzin.event.outbox.service.MessageOutboxService;

@SpringBootApplication
public class OutboxApp {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(OutboxApp.class);
        MessageOutboxService bean = context.getBean(MessageOutboxService.class);
        Message message = new Message();
        message.getHeaderMap().put("message-type", "ORDER_CREATED");
        message.setBody("{\"orderId\": \"1\"}");
        String orderId = "1";
        bean.send("annotated1", orderId, message);
        bean.send("thing1", orderId, message);
        bean.send("thing2", orderId, message);
    }
}
