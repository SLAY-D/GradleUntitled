package tests.junit5.ui.TrainElements;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Elements {
    private WebDriver driver;
    @BeforeEach
    public void setUp(){
        driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60)); // Ожидаем загрузку страницы
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15)); // Установка лимита ожидания определенного элемента
    }

    @AfterEach
    public void tearDown(){
        driver.close();
        driver.quit();
    }

    @Test
    public void textBoxTest() {
        driver.get("http://85.192.34.140:8081");
        String inputName = "Craig Thompson";
        String inputEmail = "craig@lk.com";
        String inputCurrentAddress = "Vinewood st. #12";
        String inputPermanentAddress = "El Paso rd. #9";
        driver.findElement(By.xpath("//h5[text()='Elements']")).click();
        driver.findElement(By.xpath("//span[text()='Text Box']")).click();
        driver.findElement(By.xpath("//input[@id='userName']")).sendKeys(inputName);
        driver.findElement(By.xpath("//input[@id='userEmail']")).sendKeys(inputEmail);
        driver.findElement(By.xpath("//textarea[@id='currentAddress']")).sendKeys(inputCurrentAddress);
        driver.findElement(By.xpath("//textarea[@id='permanentAddress']")).sendKeys(inputPermanentAddress);
        driver.findElement(By.xpath("//button[@id='submit']")).click();

        String name = driver.findElement(By.xpath("//p[@id='name']")).getText();
        String email = driver.findElement(By.xpath("//p[@id='email']")).getText();
        String currentAddress = driver.findElement(By.xpath("//p[@id='currentAddress']")).getText();
        String permanentAddress = driver.findElement(By.xpath("//p[@id='permanentAddress']")).getText();

        Assertions.assertEquals("Name:" + inputName, name);
        Assertions.assertEquals("Email:" + inputEmail, email);
        Assertions.assertEquals("Current Address :" + inputCurrentAddress, currentAddress);
        Assertions.assertEquals("Permananet Address :" + inputPermanentAddress, permanentAddress);
    }

    @Test
    public void textBoxErrorTest(){
        driver.get("http://85.192.34.140:8081");
        String inputName = "Craig Thompson";
        String inputEmail = "craig@l";

        driver.findElement(By.xpath("//h5[text()='Elements']")).click();
        driver.findElement(By.xpath("//span[text()='Text Box']")).click();
        driver.findElement(By.xpath("//input[@id='userName']")).sendKeys(inputName);
        driver.findElement(By.xpath("//input[@id='userEmail']")).sendKeys(inputEmail);
        driver.findElement(By.xpath("//button[@id='submit']")).click();
        WebElement errorField = driver.findElement(By.xpath("//input[@id='userEmail' and @class='mr-sm-2 field-error form-control']"));
        Assertions.assertTrue(errorField.isDisplayed());
    }

    @Test
    public void radioButtonTest(){
        driver.get("http://85.192.34.140:8081");
        driver.findElement(By.xpath("//h5[text()='Elements']")).click();
        driver.findElement(By.xpath("//span[text()='Radio Button']")).click();
        driver.findElement(By.xpath("//label[@class='custom-control-label' and @for='yesRadio']")).click();
        driver.findElement(By.xpath("//label[@class='custom-control-label' and @for='yesRadio']")).isSelected();
        driver.findElement(By.xpath("//label[@class='custom-control-label' and @for='impressiveRadio']")).click();
        Assertions.assertFalse(driver.findElement(By.xpath("//label[@class='custom-control-label' and @for='yesRadio']")).isSelected());
    }

    @Test
    public void buttonsTest(){
        driver.get("http://85.192.34.140:8081");
        driver.findElement(By.xpath("//h5[text()='Elements']")).click();
        driver.findElement(By.xpath("//span[text()='Buttons']")).click();
        String doubleClickExpectedText = "You have done a double click";
        String rightClickExpectedText = "You have done a right click";
        String dynamicClickExpectedText = "You have done a dynamic click";

        WebElement doubleClickBtn = driver.findElement(By.xpath("//button[@id='doubleClickBtn']"));
        WebElement rightClickBtn = driver.findElement(By.xpath("//button[@id='rightClickBtn']"));
        WebElement dynamicClickBtn = driver.findElement(By.xpath("//button[text()='Click Me']"));

        Actions actions = new Actions(driver);

        actions.doubleClick(doubleClickBtn).build().perform();
        actions.contextClick(rightClickBtn).build().perform();
        actions.click(dynamicClickBtn).build().perform();

        String doubleClickActualText = driver.findElement(By.xpath("//p[@id='doubleClickMessage']")).getText();
        String rightClickActualText = driver.findElement(By.xpath("//p[@id='rightClickMessage']")).getText();
        String dynamicClickActualText = driver.findElement(By.xpath("//p[@id='dynamicClickMessage']")).getText();

        Assertions.assertEquals(doubleClickExpectedText,doubleClickActualText);
        Assertions.assertEquals(rightClickExpectedText,rightClickActualText);
        Assertions.assertEquals(dynamicClickExpectedText,dynamicClickActualText);
    }


}
