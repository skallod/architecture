package ru.galuzin.store.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class OrderItem  implements Serializable {

    private static final long serialVersionUID=1L;

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE,generator = "order_item_id_seq")
    @SequenceGenerator(name="order_item_id_seq",sequenceName="order_item_id_seq")
    private long id;

    private long price;

    private int quantity;

    private long bookId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="commerce_order_id", nullable=false)
    private CommerceOrder commerceOrder;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getBookId() {
        return bookId;
    }

    public CommerceOrder getCommerceOrder() {
        return commerceOrder;
    }

    public void setCommerceOrder(CommerceOrder commerceOrder) {
        this.commerceOrder = commerceOrder;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return id == orderItem.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
