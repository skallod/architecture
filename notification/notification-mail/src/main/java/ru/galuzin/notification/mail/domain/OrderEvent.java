package ru.galuzin.notification.mail.domain;

public class OrderEvent {

    String orderId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
