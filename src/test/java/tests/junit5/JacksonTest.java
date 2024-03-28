package tests.junit5;

import org.junit.jupiter.api.Test;
import models.Cat;
import models.People;
import utils.JsonHelper;

public class JacksonTest {
    @Test
    public void testJson(){
        Cat cat = JsonHelper.fromJson("src/test/resources/cat.json",Cat.class);
        People people = JsonHelper.fromJson("src/test/resources/person.json", People.class);
        System.out.println(cat);
        System.out.println(people);

        String catJson = JsonHelper.toJson(cat);
        String peopleJson = JsonHelper.toJson(people);
        System.out.println(catJson);
        System.out.println(peopleJson);
    }
}
