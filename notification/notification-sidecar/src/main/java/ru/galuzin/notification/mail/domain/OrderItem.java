package ru.galuzin.notification.mail.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Objects;

public class OrderItem  implements Serializable {

    private static final long serialVersionUID=1L;

    private long id;

    private long price;

    private int quantity;

    private long bookId;

    @JsonIgnore
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

}
