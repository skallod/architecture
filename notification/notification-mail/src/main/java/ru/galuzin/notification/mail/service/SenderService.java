package ru.galuzin.notification.mail.service;

import ru.galuzin.notification.mail.domain.CustomerDto;

public interface SenderService {
    public void send(CustomerDto customer, String content);
}
