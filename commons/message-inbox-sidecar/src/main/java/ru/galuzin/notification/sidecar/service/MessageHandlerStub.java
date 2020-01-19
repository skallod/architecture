package ru.galuzin.notification.sidecar.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.galuzin.notification.sidecar.domain.Message;


public class MessageHandlerStub implements MessageHandlerService {

    private static final Logger logger = LoggerFactory.getLogger(MessageHandlerStub.class);

    @Override
    public void hadleMessage(Message message) {
        logger.info("event handled {}", message);
    }
}
