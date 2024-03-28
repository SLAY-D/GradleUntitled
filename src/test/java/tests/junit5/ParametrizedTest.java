package tests.junit5;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import models.People;

import java.util.stream.Stream;

public class ParametrizedTest {

    private static Stream<Arguments> testPeople(){
        return Stream.of(
                Arguments.of(new People("Craig",23,"male")),
                Arguments.of(new People("Petr",14,"male")),
                Arguments.of(new People("Albina",18,"female"))
        );
    }

    @ParameterizedTest
    @CsvSource({"Craig,23,male","Petr,14,male","Albina,18,female"})
    public void checkWordInNameCSVSource(String name, String age, String sex){
        System.out.println(name + " " + age + " " + sex);
        Assertions.assertTrue(name.contains("a"),"Имя не содержит букву 'a'");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/people.csv", delimiter = ',')
    public void checkWordInNameCSVFile(String name, String age, String sex){
        System.out.println(name + " " + age + " " + sex);
        Assertions.assertTrue(name.contains("a"),"Имя не содержит букву 'a'");
    }

    @ParameterizedTest
    @MethodSource("testPeople")
    public void checkWordInNameMethodSource(People people){
        System.out.println(people.getName() + " " + people.getAge() + " " + people.getSex());
        Assertions.assertTrue(people.getName().contains("a"),"Имя не содержит букву 'a'");
    }
}
