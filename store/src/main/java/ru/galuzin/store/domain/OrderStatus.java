package ru.galuzin.store.domain;

public enum OrderStatus {
    NEW,
    ERROR,
    BOOKED,
    MANUAL_SUPPORT,
    PAYED,
    PAY_PENDING,
    SHIPPED,
    SHIPPING_PENDING,
    COMPLETED
}
