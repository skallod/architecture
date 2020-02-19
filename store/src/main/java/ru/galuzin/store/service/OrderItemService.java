package ru.galuzin.store.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.galuzin.event.outbox.domain.Message;
import ru.galuzin.event.outbox.service.JsonConverterService;
import ru.galuzin.event.outbox.service.MessageOutboxService;
import ru.galuzin.store.domain.OrderEvent;
import ru.galuzin.store.domain.OrderItem;
import ru.galuzin.store.repository.OrderItemRepository;

@Service
public class OrderItemService {

    private static final Logger log = LoggerFactory.getLogger(OrderItemService.class);

    private final OrderItemRepository orderItemRepository;

    public OrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    public OrderItem save(OrderItem item){
        return orderItemRepository.save(item);
    }
}
