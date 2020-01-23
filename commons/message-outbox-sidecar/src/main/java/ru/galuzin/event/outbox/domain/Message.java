package ru.galuzin.event.outbox.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Message {

    public static final String MESSAGE_TYPE_HEADER = "message-type";

    String id = UUID.randomUUID().toString();

    Map<String, String> headerMap = new HashMap<String,String>();

    String body;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    @Override
    public String toString() {
        return "Message{" +
                "id='" + id + '\'' +
                ", headerMap=" + headerMap +
                ", body='" + body + '\'' +
                '}';
    }
}
