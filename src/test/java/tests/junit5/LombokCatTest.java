package tests.junit5;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import models.Cat;

public class LombokCatTest {

    @Test
    public void testCat(){
        Cat cat = new Cat("Dasha","Russian Cat","White",5);
        System.out.println(cat);

        Cat catSiam = new Cat();
        catSiam.setName("Petrovich");
        catSiam.setBreed("Siam");
        catSiam.setAge(1);
        System.out.println(catSiam);

        Cat catBuilder = Cat.builder()
                .name("Archi")
                .breed("Scotland cat")
                .color("Black")
                .age(3)
                .build();
        System.out.println(catBuilder);

        Assertions.assertEquals(catSiam, catBuilder);
    }
}
