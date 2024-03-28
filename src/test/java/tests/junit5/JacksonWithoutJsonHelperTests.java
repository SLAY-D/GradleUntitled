package tests.junit5;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import models.People;

import java.io.File;
import java.io.IOException;

public class JacksonWithoutJsonHelperTests {
    @Test
    public void testJackson() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("src/test/resources/person.json");
        People people = objectMapper.readValue(file, People.class);
        System.out.println(people.getName());
        System.out.println(people.getAge());
        System.out.println(people.getSex());

        People albina = new People("Albina", 23, "female");
        String json = objectMapper.writeValueAsString(albina);
        System.out.println(json);
    }
}
