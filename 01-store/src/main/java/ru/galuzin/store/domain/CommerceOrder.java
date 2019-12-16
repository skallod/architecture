package ru.galuzin.store.domain;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
public class CommerceOrder {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE,generator = "commerce_order_id_seq")
    @SequenceGenerator(name="commerce_order_id_seq",sequenceName="commerce_order_id_seq")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @OneToMany( mappedBy = "commerceOrder" , fetch = FetchType.LAZY )
    private Set<OrderItem> orderItemSet;

    private OrderStatus orderStatus = OrderStatus.NEW;

    public Customer getCustomerId() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Set<OrderItem> getOrderItemSet() {
        return orderItemSet;
    }

    public void setOrderItemSet(Set<OrderItem> orderItemSet) {
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
