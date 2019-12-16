package ru.galuzin.store.controller;

import org.springframework.web.bind.annotation.*;
import ru.galuzin.store.domain.CommerceOrder;
import ru.galuzin.store.service.CommerceOrderService;

@RestController
@RequestMapping("/api/v1/order")
public class CommerceOrderController {

    private final CommerceOrderService orderService;

    public CommerceOrderController(CommerceOrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    public CommerceOrder getOrder(@PathVariable("id") Long id){
        return orderService.getOne(id);
    }

    @PostMapping("/create")
    public CommerceOrder createOrder(@RequestBody CommerceOrder order){
        return orderService.save(order);
    }
}
