package tests.junit5;

import listener.RetryListener;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Tag("API")
@ExtendWith(RetryListener.class)
public class SimpleTests {

    private String date;
    private static int age = 0;

    // Выполнение методы перед запуском всего класса. Подчистить, настроить переменные и тд.
    @BeforeAll
    public static void beforeAllTests(){
        age += 10;
    }

    @AfterAll
    public static void saveFailed() {
        RetryListener.saveFailedTests();
    }

    @BeforeEach
    public void before(){
        date = "TIME: " + LocalDateTime.now();

    }

    @AfterEach
    public void after(){
        System.out.println("Тест закончен");
    }

    @Test
    public void testTwoLessThanThree(){
        System.out.println(date);
        int a = 2;
        int b = 3;
        Assertions.assertTrue(a>b, "Число " + a + " больше чем " + b);
    }

    @Test
    public void testTwoRetry(){
        int a = 2;
        age++;
        Assertions.assertEquals(a,age);
    }

    @Test
    @DisplayName("Суммирование двух чисел")
    //@Disabled("Тест исправится через неделю EWS-325") // Позволяет заглушить тест при общем прогоне. Не нужно комментить через слеши
    void testSum() {
        int a = 3;
        int b = 2;
        int sum = a + b;
        Assertions.assertEquals(5, sum);
    }
}
