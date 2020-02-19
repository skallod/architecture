package ru.galuzin.store.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Entity
public class CommerceOrder {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE,generator = "commerce_order_id_seq")
    @SequenceGenerator(name="commerce_order_id_seq",sequenceName="commerce_order_id_seq")
    private long id;

    private long customerId;

    @OneToMany( mappedBy = "commerceOrder" , fetch = FetchType.LAZY )
    private List<OrderItem> orderItemSet = new ArrayList<>();

    private OrderStatus orderStatus = OrderStatus.NEW;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommerceOrder that = (CommerceOrder) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
