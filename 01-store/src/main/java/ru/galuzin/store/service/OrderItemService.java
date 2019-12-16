package ru.galuzin.store.service;

import org.springframework.stereotype.Service;
import ru.galuzin.store.domain.OrderItem;
import ru.galuzin.store.repository.OrderItemRepository;

@Service
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;

    public OrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    public OrderItem save(OrderItem item){
        return orderItemRepository.save(item);
    }
}
