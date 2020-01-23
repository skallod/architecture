package ru.galuzin.notification.mail.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.galuzin.notification.mail.domain.OrderEvent;

@Service
public class OrderEventHandler {

    private static final Logger log = LoggerFactory.getLogger(OrderEventHandler.class);

    public void orderCreated(OrderEvent orderEvent){
        log.info("order created event was got");
    }
}
