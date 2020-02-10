package ru.galuzin.notification.mail.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;
import ru.galuzin.notification.mail.domain.CommerceOrder;
import ru.galuzin.notification.mail.domain.CustomerDto;
import ru.galuzin.notification.mail.domain.OrderEvent;
import ru.galuzin.notification.mail.domain.OrderItem;

import java.util.stream.Collectors;

public class OrderEventHandler {

    private static final Logger log = LoggerFactory.getLogger(OrderEventHandler.class);

    private final RestTemplate restTemplate;

    private final String storeService;

    private final SenderService sender;

    public OrderEventHandler(RestTemplate restTemplate, String storeService, SenderService sender) {
        this.restTemplate = restTemplate;
        this.storeService = "http://"+storeService;
        this.sender = sender;
    }

    public void orderCreated(OrderEvent orderEvent){
        log.info("order created event was got, order id {}", orderEvent.getOrderId());
        CommerceOrder order = restTemplate.getForObject( storeService +
                "/api/v1/order/" + orderEvent.getOrderId(), CommerceOrder.class);
        CustomerDto customer = restTemplate.getForObject(storeService +
                "/api/v1/customer/" + order.getCustomerId(), CustomerDto.class);
        long price = order.getOrderItemSet().stream().map(OrderItem::getPrice).reduce(0L, Long::sum);
        String elms = order.getOrderItemSet().stream().map(item -> String.valueOf(item.getBookId()))
                .collect(Collectors.joining(","));
        String content = "Заказ создан, номер заказа " + order.getId() + ", цена " + price
                + ", артикулы товаров: " + elms;
        sender.send(customer, content);
    }
}
