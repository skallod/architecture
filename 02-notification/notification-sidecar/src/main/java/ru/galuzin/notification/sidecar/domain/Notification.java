package ru.galuzin.notification.sidecar.domain;

import java.io.Serializable;
import java.util.Map;
import java.util.Properties;

public class Notification {

    Map<String, String> headerMap;

    String body;

    public Map<String, String> getHeaderMap() {
        return headerMap;
    }

    public void setHeaderMap(Map<String, String> headerMap) {
        this.headerMap = headerMap;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
