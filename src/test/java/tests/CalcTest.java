package tests;

import calc.CalcSteps;
import io.qameta.allure.Allure;
import io.qameta.allure.Issue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

public class CalcTest {

    // 1 способ Allure Steps с использованием внешнего класса CalcSteps()
    @Test
    public void sumTest(){
        CalcSteps calcSteps = new CalcSteps();
        int result = calcSteps.sum(2,5);
        boolean isPositive = calcSteps.isPositive(result);
        Assertions.assertTrue(isPositive);
    }

    // 2 способ Allure Steps с использованием Allure.step
    @Test
    @Issue("STUDIOSOL-183") // Шаблон для задачи указан в файле resources/allure.properties
    public void sumStepsTest(){
        int a = 5;
        int b = 4;
        AtomicInteger result = new AtomicInteger(); // Atomic - пространство переменной, к которому можно обратиться с любого места
        Allure.step("Прибавляем " + a + " к переменной " + b, step -> {
            result.set(a + b);
        });
        Allure.step("Проверям, что результат " + result.get() + " больше нуля", x->{
            Assertions.assertTrue(result.get() > 0);
        });

    }
}
