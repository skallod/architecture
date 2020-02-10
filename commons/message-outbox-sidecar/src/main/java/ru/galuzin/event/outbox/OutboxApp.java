package ru.galuzin.event.outbox;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.PropertySource;
import ru.galuzin.event.outbox.domain.Message;
import ru.galuzin.event.outbox.service.MessageOutboxService;

@SpringBootApplication
public class OutboxApp {

    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext context = SpringApplication.run(OutboxApp.class);
        MessageOutboxService bean = context.getBean(MessageOutboxService.class);
        Message message = new Message();
        message.getHeaderMap().put("message-type", "ORDER_CREATED");
        message.setBody("{\"orderId\": \"1\"}");
        String orderId = "1";
        bean.send("thing1", orderId, message);
        while (true) {
            bean.send("orders-topic", orderId, message);
            Thread.sleep(10_000);
        }
    }
}
