package com.batook.ex2;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.text.SimpleDateFormat;

public class JsonConverter {

    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        mapper.setDateFormat(dateFormat);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, true);
    }

    public static <T> T fromJsonString(String json, Class<T> klass) throws IOException {
        return mapper.readValue(json, klass);
    }

    public static <T> String toJsonString(T obj) throws JsonProcessingException {
        return mapper.writeValueAsString(obj);
    }

}