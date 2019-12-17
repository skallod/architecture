package ru.galuzin.store.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.galuzin.store.domain.CommerceOrder;
import ru.galuzin.store.repository.CommerceOrderRepository;
import ru.galuzin.store.repository.OrderItemRepository;

@Service
public class CommerceOrderService {

    private final CommerceOrderRepository orderRepository;

    private final OrderItemRepository orderItemRepository;

    public CommerceOrderService(CommerceOrderRepository orderRepository, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    public CommerceOrder getOne(long id){
        return orderRepository.findById(id).orElse(null);
    }

    @Transactional
    public CommerceOrder save(CommerceOrder order){
        CommerceOrder orderWithId = orderRepository.save(order);
        order.getOrderItemSet().forEach(orderItem -> {
            orderItem.setCommerceOrder(orderWithId);
            orderItemRepository.save(orderItem);
        });
        return orderWithId;
    }
}
