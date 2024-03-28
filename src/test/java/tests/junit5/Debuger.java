package tests.junit5;

import org.junit.jupiter.api.Test;
import models.Cat;
import models.LateInitExample;

import java.util.ArrayList;
import java.util.List;

public class Debuger {
    @Test
    public void catTest(){
        Cat catTest = Cat.builder()
                .name("Archi")
                .breed("Scotland cat")
                .color("Black")
                .age(5)
                .build();

        int catAge = catTest.getAge() + 5;
        System.out.println(catAge);

        LateInitExample lateInitExample = new LateInitExample();
        System.out.println(lateInitExample.getPeopleCount());

        List<String> names = new ArrayList<>();
        names.add("Craig");
        names.add("Archi");
    }
}
