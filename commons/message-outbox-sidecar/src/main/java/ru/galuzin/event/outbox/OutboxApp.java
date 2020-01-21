package ru.galuzin.event.outbox;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.galuzin.event.outbox.domain.Message;
import ru.galuzin.event.outbox.service.SendService;

@SpringBootApplication
public class OutboxApp {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(OutboxApp.class);
        SendService bean = context.getBean(SendService.class);
        Message message = new Message();
        message.getHeaderMap().put("event-type", "ORDER_CREATED");
        message.setBody("{\"orderId\": \"1\"}");
        bean.send("annotated1", message);
        bean.send("thing1", message);
        bean.send("thing2", message);
    }
}
