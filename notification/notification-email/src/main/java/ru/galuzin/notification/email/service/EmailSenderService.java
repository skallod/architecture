package ru.galuzin.notification.email.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.galuzin.notification.mail.domain.CustomerDto;
import ru.galuzin.notification.mail.service.SenderService;

public class EmailSenderService implements SenderService {

    private static final Logger log = LoggerFactory.getLogger(EmailSenderService.class);

    @Override
    public void send(CustomerDto customer, String content) {
        log.info("MAIL SEND to email {}, mail content [ {} ]", customer.email , content);
    }
}
