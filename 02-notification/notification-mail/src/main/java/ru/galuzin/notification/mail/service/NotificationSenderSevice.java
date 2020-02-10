package ru.galuzin.notification.mail.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.galuzin.notification.mail.domain.CustomerDto;

public class NotificationSenderSevice implements SenderService {

    private static final Logger log = LoggerFactory.getLogger(NotificationSenderSevice.class);

    public void send( CustomerDto customer, String content){
        log.info("MAIL SEND to email {}, mail content [ {} ]", customer.email , content);
    }
}
