package uz.pdp.shippingservice.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.SneakyThrows;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;
import org.json.XML;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Base64;
import java.util.Map;

public class AppUtils {

    private static final Gson gson = new Gson().newBuilder()
            .create();
    private static final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);

    private static final Base64.Encoder base64Encoder = Base64.getEncoder();
    private static final Base64.Decoder base64Decoder = Base64.getDecoder();

    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }



    public static String generateJsonRPCID() {
        return RandomStringUtils.random(6, "0123456789");
    }

    public static String getJsonAsString(Object o) {
        return gson.toJson(o);
    }

    @SneakyThrows
    public static String getJsonAsStringByJackson(Object o) {
        return objectMapper.writeValueAsString(o);
    }

    public static <T> T jsonTreeToObjectByGson(String jsonTree, Class<T> tClass) {
        return gson.fromJson(jsonTree, tClass);
    }

    public static <T> T jsonTreeToObjectByJackson(String jsonTree, Class<T> tClass) throws JsonProcessingException {
        return objectMapper.convertValue(objectMapper.readTree(jsonTree), tClass);
    }

    public static <T> T jsonTreeToObjectByJackson(String jsonTree, Class<T> tClass, String... afterKeys) throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(jsonTree);
        for (String afterKey : afterKeys) {
            jsonNode = jsonNode.get(afterKey);
        }
        return objectMapper.convertValue(jsonNode, tClass);
    }

    public static <T> T jsonTreeToObjectByJackson(String jsonTree, TypeReference<T> typeReference, String... afterKeys) throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(jsonTree);
        for (String afterKey : afterKeys) {
            jsonNode = jsonNode.get(afterKey);
        }
        return objectMapper.convertValue(jsonNode, typeReference);
    }

    public static <T> T jsonTreeToObjectByJackson(String jsonTree, TypeReference<T> typeReference) throws JsonProcessingException {
        return objectMapper.convertValue(objectMapper.readTree(jsonTree), typeReference);
    }

    public static Map<String, Object> jsonTreeToMapByGson(String jsonTree) {
        return gson.fromJson(jsonTree, Map.class);
    }

    public static <T> T convert(Object o, Class<T> tClass, String... keys) {
        for (String key : keys) {
            o = jsonTreeToMapByGson(gson.toJson(o)).get(key);
        }
        return gson.fromJson(gson.toJsonTree(o), tClass);
    }

    public static <T> T convert(Object o, Class<T> tClass) {
        return gson.fromJson(gson.toJsonTree(o), tClass);
    }

    public static <T> T convertWithJackson(Object o, Class<T> tClass) {
        return objectMapper.convertValue(o, tClass);
    }

    public static <T> T convertWithJackson(Object o, TypeReference<T> typeReference) {
        return objectMapper.convertValue(o, typeReference);
    }

    public static <T> T convert(Object o, Type type) {
        return gson.fromJson(gson.toJsonTree(o), type);
    }

    public static <T> T convertXmlToObject(String xml, Class<T> tClass) {
        return jsonTreeToObjectByGson(XML.toJSONObject(xml).toString(), tClass);
    }

    public static <T> T convertXmlToObject(String xml, Class<T> tClass, String... keys) {
        JSONObject jsonObject = XML.toJSONObject(xml);
        for (String key : keys) {
            jsonObject = jsonObject.getJSONObject(key);
        }
        return jsonTreeToObjectByGson(jsonObject.toString(), tClass);
    }

    public static <T> T convertXmlToObjectWithJackson(String xml, Class<T> tClass, String... keys) throws JsonProcessingException {
        JSONObject jsonObject = XML.toJSONObject(xml);
        for (String key : keys) {
            jsonObject = jsonObject.getJSONObject(key);
        }
        return jsonTreeToObjectByJackson(jsonObject.toString(), tClass);
    }



    public static String getResourceFileAsString(String path) throws URISyntaxException, IOException {
        return Files.readString(getResourceFile(path).toPath());
    }

    public static File getResourceFile(String path) throws URISyntaxException, IOException {
        URL url = AppUtils.class.getClassLoader().getResource(path);
        assert url != null;
        return new File(url.toURI());
    }

    public static String encodeFromStringToBase64(String str) {
        return base64Encoder.encodeToString(str.getBytes());
    }

    public static String decodeFromBase64ToString(String str) {
        return base64Encoder.encodeToString(str.getBytes());
    }

    public static String getBasicAuthentication(String username, String password) {
        String forEncodeString = username
                .concat(":")
                .concat(password);

        return "Basic ".concat(encodeFromStringToBase64(forEncodeString));
    }

}
