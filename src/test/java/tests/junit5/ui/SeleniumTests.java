package tests.junit5.ui;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.time.Duration;


public class SeleniumTests {

    private WebDriver driver;

    @BeforeEach
    public void setUp(){
//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--headless"); // Запуск браузера в невидимом окне. В основном используется на CI/CD
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().setSize(new Dimension(1920,1080));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60)); // Ожидаем загрузку страницы
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60)); // Установка лимита ожидания определенного элемента
    }

    @AfterEach
    public void tearDown(){
        driver.close();
    }

    @Test
    public void simpleTest(){
        String expectedTitle = "Flavour Trip - YouTube";

        driver.get("https://youtube.com/@flavourtrip");
        String actualTitle = driver.getTitle();

        Assertions.assertEquals(expectedTitle, actualTitle);
    }
}
