package ru.galuzin.store.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.galuzin.event.outbox.domain.Message;
import ru.galuzin.event.outbox.service.JsonConverterService;
import ru.galuzin.event.outbox.service.MessageOutboxService;
import ru.galuzin.store.domain.CommerceOrder;
import ru.galuzin.store.domain.EventType;
import ru.galuzin.store.domain.OrderEvent;
import ru.galuzin.store.domain.OrderItem;

import java.util.List;

@Service
public class CommerceOrderService {

    private final Logger log = LoggerFactory.getLogger(CommerceOrderService.class);

    private final MessageOutboxService messageOutboxService;

    private final JsonConverterService jsonConverterService;

    private final String ordersTopic;

    private final CommerceOrderDao commerceOrderDao;

    public CommerceOrderService(MessageOutboxService messageOutboxService,
                                JsonConverterService jsonConverterService,
                                CommerceOrderDao commerceOrderDao,
                                @Value("${orders.topic}")String ordersTopic
                                ) {
        this.messageOutboxService = messageOutboxService;
        this.jsonConverterService = jsonConverterService;
        this.ordersTopic = ordersTopic;
        this.commerceOrderDao = commerceOrderDao;
    }

    public CommerceOrder create(CommerceOrder order){
        CommerceOrder savedOrder = commerceOrderDao.save(order);
        Message message = new Message();
        OrderEvent orderEvent = new OrderEvent();
        String orderId = String.valueOf(savedOrder.getId());
        orderEvent.setOrderId(orderId);
        message.getHeaderMap().put(Message.MESSAGE_TYPE_HEADER, EventType.ORDER_CREATED.name());
        try {
            message.setBody(jsonConverterService.toJson(orderEvent));
            messageOutboxService.send(ordersTopic, orderId, message);
        } catch (Exception e) {
            log.error("send create order event fail",e);
        }
        return savedOrder;
    }

    public CommerceOrder getOne (Long id){
        return commerceOrderDao.getOne(id);
    }
}
