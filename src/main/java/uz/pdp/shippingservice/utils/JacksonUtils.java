package uz.pdp.shippingservice.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.io.IOException;

@Data
public class JacksonUtils {
    private final static ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
    }

    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public static String objectAsString(Object o) {
        try {
            return objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static <T> T jsonStringToObject(String json, Class<T> tClass) throws IOException {
        return objectMapper.readValue(json, tClass);
    }

    public static <T> T jsonStringToObject(String json, JavaType javaType) throws IOException {
        return objectMapper.readValue(json, javaType);
    }

    public static <T> T jsonStringToObject(String json, TypeReference<T> typeReference) throws IOException {
        return objectMapper.readValue(json, typeReference);
    }

    public static <T> T convertObject(Object obj, TypeReference<T> typeReference) throws IOException {
        return objectMapper.convertValue(obj, typeReference);
    }


    public static <T> T convertObject(Object obj, Class<T> tClass) {
        return objectMapper.convertValue(obj, tClass);
    }
}
