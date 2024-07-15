package tests.junit5.ui;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HardElementsTests {
    private WebDriver driver;
    @BeforeEach
    public void setUp(){
        driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60)); // Ожидаем загрузку страницы
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60)); // Установка лимита ожидания определенного элемента
    }

    @AfterEach
    public void tearDown(){
        driver.close();
        driver.quit();
    }

    // Basic Auth
    @Test
    public void authTest(){
        //driver.get("https://the-internet.herokuapp.com/basic_auth");
        driver.get("https://admin:admin@the-internet.herokuapp.com/basic_auth"); // Если Basic Auth, то logpass можно подставить прямо в URL
        String h3 = driver.findElement(By.xpath("//h3[text()='Basic Auth']")).getText();
        Assertions.assertEquals("Basic Auth", h3);
    }

    @Test
    public void alertOkTest(){
        String expectedText = "I am a JS Alert";
        String expectedResult = "You successfully clicked an alert";

        driver.get("https://the-internet.herokuapp.com/javascript_alerts");
        driver.findElement(By.xpath("//button[@onclick='jsAlert()']")).click();
        String actualText = driver.switchTo().alert().getText(); // Получение текста из алерта
        driver.switchTo().alert().accept();
        String result = driver.findElement(By.id("result")).getText();
        Assertions.assertEquals(expectedText, actualText);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    public void alertConfirmOkTest(){
        String expectedText = "I am a JS Confirm";
        String expectedResult = "You clicked: Ok";

        driver.get("https://the-internet.herokuapp.com/javascript_alerts");
        driver.findElement(By.xpath("//button[@onclick='jsConfirm()']")).click();
        String actualText = driver.switchTo().alert().getText(); // Получение текста из алерта
        driver.switchTo().alert().accept();
        String result = driver.findElement(By.id("result")).getText();
        Assertions.assertEquals(expectedText, actualText);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    public void alertConfirmCancelTest(){
        String expectedText = "I am a JS Confirm";
        String expectedResult = "You clicked: Cancel";

        driver.get("https://the-internet.herokuapp.com/javascript_alerts");
        driver.findElement(By.xpath("//button[@onclick='jsConfirm()']")).click();
        String actualText = driver.switchTo().alert().getText(); // Получение текста из алерта
        driver.switchTo().alert().dismiss();
        String result = driver.findElement(By.id("result")).getText();
        Assertions.assertEquals(expectedText, actualText);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    public void alertPromptTest(){
        String expectedText = "I am a JS prompt";
        String enterText = "Check prompt";
        String expectedResult = "You entered: " + enterText;

        driver.get("https://the-internet.herokuapp.com/javascript_alerts");
        driver.findElement(By.xpath("//button[@onclick='jsPrompt()']")).click();
        String actualText = driver.switchTo().alert().getText(); // Получение текста из алерта
        driver.switchTo().alert().sendKeys(enterText);
        driver.switchTo().alert().accept();
        String result = driver.findElement(By.id("result")).getText();
        Assertions.assertEquals(expectedText, actualText);
        Assertions.assertEquals(expectedResult, result);
    }

    // Работа с iframe
    @Test
    public void mailLoginTest(){
        driver.get("https://mail.ru/");
        driver.findElement(By.xpath("//button[@class='ph-login svelte-1uggkp4']")).click();
        WebElement iframeAuth = driver.findElement(By.xpath("//iframe[@class='ag-popup__frame__layout__iframe']"));
        driver.switchTo().frame(iframeAuth); // Переключение на iframe
        driver.findElement(By.xpath("//input[@name='username']")).sendKeys("gamarjoba@mail.ru");
    }


    /*
        Если не получается найти элемент на странице, потому что элемент появляется и быстро пропадает, либо выпадающий список
         закрывается после нажатия на поиск локатора через девтулс. Использовать скрипт с таймаутом в консоли девтулс:
         setTimeout(function() {
            debugger;
         }, 3000)

         Как вариант еще можно использовать CTRL + SHIFT + C
     */

    @Test
    public void selectMenuTest(){
        String correctTextMenu = "Mr.";
        driver.get("http://85.192.34.140:8081");
        driver.findElement(By.xpath("//h5[text()='Widgets']")).click();
        driver.findElement(By.xpath("//span[text()='Select Menu']")).click();
        driver.findElement(By.xpath("//div[text()='Select Title']")).click();
        /*
            Если по дереву отсутствуют дочерние элементы, то использовать "following::"
         */
        driver.findElement(By.xpath("//div[text()='Pick one title']//following::div[text()='Mr.']")).click();
        String textMenu = driver.findElement(By.xpath("//div[@class=' css-1uccc91-singleValue']")).getText();
        Assertions.assertEquals(correctTextMenu,textMenu);
    }

    @Test
    public void sliderTest(){
        driver.get("http://85.192.34.140:8081");
        driver.findElement(By.xpath("//h5[text()='Widgets']")).click();
        driver.findElement(By.xpath("//span[text()='Slider']")).click();
        WebElement slider = driver.findElement(By.xpath("//input[@type='range']"));
//        Actions actions = new Actions(driver);
//        actions.dragAndDropBy(slider,50,0).build().perform(); // Перемещение по координатам через actions
        int expectedValue = 85;
        int currentValue = Integer.parseInt(slider.getAttribute("value"));
        int valueToMove = expectedValue - currentValue;
        for (int i = 0; i < valueToMove; i++) {
            slider.sendKeys(Keys.ARROW_RIGHT);
        }

        WebElement sliderValue = driver.findElement(By.id("sliderValue"));
        int actualValue = Integer.parseInt(sliderValue.getAttribute("value"));
        Assertions.assertEquals(expectedValue,actualValue);
    }

    @Test
    public void hoverTest(){
        driver.get("http://85.192.34.140:8081");
        driver.findElement(By.xpath("//h5[text()='Widgets']")).click();
        driver.findElement(By.xpath("//span[text()='Menu']")).click();
        WebElement menuItemMiddle = driver.findElement(By.xpath("//a[text()='Main Item 2']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(menuItemMiddle).build().perform();
        WebElement subList = driver.findElement(By.xpath("//a[text()='SUB SUB LIST »']"));
        actions.moveToElement(subList).build().perform();

        List<WebElement> lastElements = driver.findElements(By.xpath("//a[contains(text(), 'Sub Sub Item')]"));
        Assertions.assertEquals(2, lastElements.size());
    }
}


