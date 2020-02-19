package ru.galuzin.notification.sms.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.galuzin.notification.mail.domain.CustomerDto;
import ru.galuzin.notification.mail.service.SenderService;

public class NotificationSmsSenderService implements SenderService {

    private static final Logger log = LoggerFactory.getLogger(NotificationSmsSenderService.class);

    public void send(CustomerDto customer, String content){
        Logger log = LoggerFactory.getLogger(NotificationSmsSenderService.class);
        log.info("SMS SEND to number {}, message content [ {} ]", customer.phone , content);
    }
}
