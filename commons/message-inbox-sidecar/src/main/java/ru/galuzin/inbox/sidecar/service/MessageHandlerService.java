package ru.galuzin.inbox.sidecar.service;

import ru.galuzin.inbox.sidecar.domain.Message;

public interface MessageHandlerService {

    void hadleMessage(Message event);
}
