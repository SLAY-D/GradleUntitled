package tests.junit5.ui;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;


public class SeleniumTests {

    private WebDriver driver;
    private String downloadFolder = System.getProperty("user.dir") + File.separator + "build" + File.separator + "downloadFiles";

    @BeforeAll
    public static void downloadDriver(){
        WebDriverManager.chromedriver().setup();
    }


    @BeforeEach
    public void setUp(){
        ChromeOptions options = new ChromeOptions();
        // Опции для скачивания файла в определенную директорию
        Map<String, String> prefs = new HashMap<>();
        prefs.put("download.default_directory", downloadFolder);
        options.setExperimentalOption("prefs", prefs);

//        options.addArguments("--headless"); // Запуск браузера в невидимом окне. В основном используется на CI/CD
        driver = new ChromeDriver(options);
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

    @Test
    public void simpleFormTest(){
        String expectedName = "Craig Johnson";
        String expectedEmail = "craigtoptester@gmail.com";
        String expectedCurrentAddress = "Moscow";
        String expectedPermanentAddress = "Florida";

        driver.get("http://85.192.34.140:8081/");
        WebElement elementsCard = driver.findElement(By.xpath("//div[@class='card-body']//h5[text()='Elements']"));
        elementsCard.click();
        WebElement elementsTextBox = driver.findElement(By.xpath("//span[text()='Text Box']"));
        elementsTextBox.click();

        WebElement fullName = driver.findElement(By.id("userName"));
        WebElement email = driver.findElement(By.id("userEmail"));
        WebElement currentAddress = driver.findElement(By.id("currentAddress"));
        WebElement permanentAddress = driver.findElement(By.id("permanentAddress"));
        WebElement submitButton = driver.findElement(By.id("submit"));

        fullName.sendKeys(expectedName);
        email.sendKeys(expectedEmail);
        currentAddress.sendKeys(expectedCurrentAddress);
        permanentAddress.sendKeys(expectedPermanentAddress);
        submitButton.click();

        WebElement fullNameNew = driver.findElement(By.id("name"));
        WebElement emailNew = driver.findElement(By.id("email"));
        WebElement currentAddressNew = driver.findElement(By.xpath("//div[@id='output']//p[@id='currentAddress']"));
        WebElement permanentAddressNew = driver.findElement(By.xpath("//div[@id='output']//p[@id='permanentAddress']"));

        String actualName = fullNameNew.getText();
        String actualEmail = emailNew.getText();
        String actualCurrentAddress = currentAddressNew.getText();
        String actualPermanentAddress = permanentAddressNew.getText();

        Assertions.assertTrue(actualName.contains(expectedName));
        Assertions.assertTrue(actualEmail.contains(expectedEmail));
        Assertions.assertTrue(actualCurrentAddress.contains(expectedCurrentAddress));
        Assertions.assertTrue(actualPermanentAddress.contains(expectedPermanentAddress));

    }

    @Test
    public void UploadFileTest(){
        driver.get("http://85.192.34.140:8081/");
        WebElement elementsCard = driver.findElement(By.xpath("//div[@class='card-body']//h5[text()='Elements']"));
        elementsCard.click();
        WebElement elementsUpload = driver.findElement(By.xpath("//span[text()='Upload and Download']"));
        elementsUpload.click();

        WebElement uploadFileBtn = driver.findElement(By.id("uploadFile"));
        uploadFileBtn.sendKeys(System.getProperty("user.dir") + "/src/test/resources/kuzya.jpg"); // Необходимо для того чтобы не указывать абсолютный путь до файла. Подтягивается текущая директория пользователя, в которой лежит проект

        WebElement uploadedFakePath = driver.findElement(By.id("uploadedFilePath"));
        Assertions.assertTrue(uploadedFakePath.getText().contains("kuzya.jpg"));
    }

    @Test
    public void DownloadFileTest(){
        driver.get("http://85.192.34.140:8081/");
        WebElement elementsCard = driver.findElement(By.xpath("//div[@class='card-body']//h5[text()='Elements']"));
        elementsCard.click();
        WebElement elementsUpload = driver.findElement(By.xpath("//span[text()='Upload and Download']"));
        elementsUpload.click();

        WebElement downloadFileBtn = driver.findElement(By.id("downloadButton"));
        downloadFileBtn.click();
        int a = 0;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(x-> Paths.get(downloadFolder,"sticker.png").toFile().exists()); // Ждем пока файл не появится в директории
        File file = new File("/build/downloadFiles/sticker.png");
        Assertions.assertTrue(file.length()!=0);
        //Assertions.assertNotNull(file);
    }
}
