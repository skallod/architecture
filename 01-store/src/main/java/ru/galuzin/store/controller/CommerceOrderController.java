package ru.galuzin.store.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import ru.galuzin.store.domain.CommerceOrder;
import ru.galuzin.store.domain.security.UserDetailsImpl;
import ru.galuzin.store.service.CommerceOrderService;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/order")
public class CommerceOrderController {

    private static final Logger log = LoggerFactory.getLogger(CommerceOrderController.class);

    private final CommerceOrderService orderService;

    public CommerceOrderController(CommerceOrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    public CommerceOrder getOrder(@PathVariable("id") Long id){
        return orderService.getOne(id);
    }

    @PostMapping("/add")
    public CommerceOrder createOrder(@RequestBody CommerceOrder order, Principal principal){
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken)principal;
        UserDetailsImpl userDetails = (UserDetailsImpl) token.getPrincipal();
        if(order.getCustomerId() != userDetails.getCustomerId()){
            throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
        }
        return orderService.save(order);
    }
}
