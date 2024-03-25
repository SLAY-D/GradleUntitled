package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.io.File;
import java.io.IOException;

public class JsonHelper {
    private static final ObjectMapper mapper = new ObjectMapper();

    @SneakyThrows
    public static <T> T fromJson (String jsonPath, Class<T> out) {
        return mapper.readValue(new File(jsonPath),out);

    }

    @SneakyThrows
    public static <T> T fromJsonString (String json, Class<T> out) {
        return mapper.readValue(json,out);

    }

    @SneakyThrows // Автоматически обрабатывает исключения
    public static String toJson(Object object){
        return mapper.writeValueAsString(object);
    }
}
