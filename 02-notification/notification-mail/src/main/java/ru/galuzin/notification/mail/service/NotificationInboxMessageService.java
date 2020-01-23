package ru.galuzin.notification.mail.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.galuzin.notification.mail.domain.EventType;
import ru.galuzin.notification.mail.domain.OrderEvent;
import ru.galuzin.inbox.sidecar.domain.Message;
import ru.galuzin.inbox.sidecar.service.JsonConverterService;
import ru.galuzin.inbox.sidecar.service.MessageHandlerService;

import java.util.Objects;

public class NotificationInboxMessageService implements MessageHandlerService {

    private static final Logger log = LoggerFactory.getLogger(NotificationInboxMessageService.class);

    private final JsonConverterService jsonConverterService;

    private final OrderEventHandler orderEventHandler;

    public NotificationInboxMessageService(JsonConverterService jsonConverterService,
                                           OrderEventHandler orderEventHandler) {
        this.jsonConverterService = jsonConverterService;
        this.orderEventHandler = orderEventHandler;
    }

    @Override
    public void hadleMessage(Message event) {
        String eventTypeStr = event.getHeaderMap().get(Message.MESSAGE_TYPE_HEADER);
        log.info("message type {}", eventTypeStr);
        try {
            Objects.nonNull(eventTypeStr);
            EventType eventType = EventType.valueOf(eventTypeStr);
            switch (eventType) {
                case ORDER_CREATED:
                    OrderEvent orderEvent = jsonConverterService.fromJson(event.getBody(), OrderEvent.class);
                    log.info("order id {}", orderEvent.getOrderId());
                    orderEventHandler.orderCreated(orderEvent);
                    break;
            }
        }catch (Exception e){
            log.error("message handle fail", e);
        }
    }
}
