package ru.galuzin.notification.mail.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CommerceOrder {

    private long id;

    private long customerId;

    private List<OrderItem> orderItemSet = new ArrayList<>();

    private OrderStatus orderStatus;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public List<OrderItem> getOrderItemSet() {
        return orderItemSet;
    }

    public void setOrderItemSet(List<OrderItem> orderItemSet) {
        this.orderItemSet = orderItemSet;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Override
    public String toString() {
        return "CommerceOrder{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", orderItemSet=" + orderItemSet +
                ", orderStatus=" + orderStatus +
                '}';
    }
}
