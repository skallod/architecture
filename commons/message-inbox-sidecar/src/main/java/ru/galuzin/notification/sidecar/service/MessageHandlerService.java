package ru.galuzin.notification.sidecar.service;

import ru.galuzin.notification.sidecar.domain.Message;

public interface MessageHandlerService {

    void hadleMessage(Message event);
}
