package tests.testng;

import com.codeborne.selenide.Selenide;
import org.testng.annotations.Test;

public class ParallelTests {
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
