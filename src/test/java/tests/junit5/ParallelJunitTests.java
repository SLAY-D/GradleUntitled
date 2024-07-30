package tests.junit5;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;


public class ParallelJunitTests {
    @Test
    public void parallel1Test(){
        Selenide.open("https://mvnrepository.com/");
    }

    @Test
    public void parallel2Test(){
        Selenide.open("https://threadqa.ru/");
    }

    @Test
    public void parallel3Test(){
        Selenide.open("https://uniticket.ru/");
    }
}
