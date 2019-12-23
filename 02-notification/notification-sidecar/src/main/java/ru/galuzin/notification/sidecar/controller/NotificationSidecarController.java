package ru.galuzin.notification.sidecar.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.galuzin.notification.sidecar.domain.Notification;

@RestController
@RequestMapping("/api/v1/notification")
public class NotificationSidecarController {

    public ResponseEntity post(@RequestBody Notification notification){
        return ResponseEntity.ok().build();
    }
}
