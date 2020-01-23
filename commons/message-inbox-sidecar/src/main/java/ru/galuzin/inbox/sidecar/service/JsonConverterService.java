package ru.galuzin.inbox.sidecar.service;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonConverterService {

    //    static final ThreadLocal<DateFormat> DATE_FORMAT =
//            new ThreadLocal<DateFormat>() {
//                @Override
//                protected DateFormat initialValue() {
//                    return new SimpleDateFormat("yyyy-MM-dd");
//                }
//            };

    private final ObjectMapper mapper = new ObjectMapper();

    public <T> String toJson(T sdi) throws Exception {
        return mapper.writeValueAsString(sdi);
    }

    public <T> T fromJson(String json, Class<T> cls) throws Exception {
        if (json == null) {
            return null;
        }
        return mapper.readValue(json, cls);
    }
}
