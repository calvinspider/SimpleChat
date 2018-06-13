package org.yang.zhang.utils;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {
    private final static ObjectMapper om = new ObjectMapper();

    static {
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return om.readValue(json, clazz);
        } catch (IOException e) {
            return null;
        }
    }

    public static <T> T fromJson(String json, TypeReference type) {
        try {
            return om.readValue(json, type);
        } catch (IOException e) {
            return null;
        }
    }

    public static String toJson(Object obj) {
        try {
            return om.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public static JsonNode readTree(String json) {
        try {
            return om.readTree(json);
        } catch (IOException e) {
            return null;
        }
    }
}
