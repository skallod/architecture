package ru.galuzin.store.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.galuzin.store.domain.CommerceOrder;
import ru.galuzin.store.repository.CommerceOrderRepository;

@Service
public class CommerceOrderService {

    private final CommerceOrderRepository orderRepository;

    public CommerceOrderService(CommerceOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public CommerceOrder getOne(long id){
        return orderRepository.findById(id).orElse(null);
    }

    @Transactional
    public CommerceOrder save(CommerceOrder order){
        return orderRepository.save(order);
    }
}
