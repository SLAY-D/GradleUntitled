package tests.screenshots;

import com.codeborne.selenide.*;
import io.qameta.allure.Allure;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;

import static com.codeborne.selenide.Selenide.$x;

public class ScreenshotsTest {

    private String testName;
    private static File outputDir;

    @BeforeEach
    public void initTestName(TestInfo info){ // Присвоение имени тесту
        testName = info.getTestMethod().get().getName();
    }

    @BeforeAll
    public static void initFolder(){
        outputDir = new File("build/screenshots");
        if(!outputDir.exists()){
            outputDir.mkdirs();
        }
    }

    @AfterEach
    public void tearDown(){
        Selenide.closeWindow();
        Configuration.browserCapabilities = new SelenideConfig().browserCapabilities();
    }

    @Test
    public void web1080pTest(){
        Configuration.browserSize = "1920x1080";
        Selenide.open("https://threadqa.ru");
        assertFullScreen();
    }

    @Test
    public void mobileIphoneXRTest(){
        //System.setProperty("chromeoptions.mobileEmulation", "deviceName=iPhone XR");
        Configuration.browserSize = "414x896";
        Selenide.open("https://threadqa.ru");
        assertFullScreen(By.xpath("//div[@class='left-part']"));
    }

    @SneakyThrows
    private void assertFullScreen(){
        Screenshot screenshot = new AShot()
                .shootingStrategy(ShootingStrategies.viewportPasting(3000)) // Скролл экрана вниз с таймаутом в секунду (опционально)
                .takeScreenshot(WebDriverRunner.getWebDriver());
        File actualScreen = new File(outputDir.getAbsolutePath() + "/" + testName + ".png");
        ImageIO.write(screenshot.getImage(), "png", actualScreen);

        File expectedScreen = new File(String.format("src/test/resources/references/%s.png", testName));
        if(!expectedScreen.exists()){
            throw new RuntimeException("No reference image. Download it from /build/screenshots");
        }
        assertImages(actualScreen, expectedScreen);
    }

    @SneakyThrows
    private void assertFullScreen(By ignoredElement){
        Screenshot screenshot = new AShot()
                .shootingStrategy(ShootingStrategies.viewportPasting(3000)) // Скролл экрана вниз с таймаутом в секунду (опционально)
                .addIgnoredElement(ignoredElement) // Игнорирование динамического элемента
                .takeScreenshot(WebDriverRunner.getWebDriver());
        File actualScreen = new File(outputDir.getAbsolutePath() + "/" + testName + ".png");
        ImageIO.write(screenshot.getImage(), "png", actualScreen);

        File expectedScreen = new File(String.format("src/test/resources/references/%s.png", testName));
        if(!expectedScreen.exists()){
            throw new RuntimeException("No reference image. Download it from /build/screenshots");
        }
        assertImages(actualScreen, expectedScreen);
    }

    @SneakyThrows
    private void assertImages(File actualFile, File expectedFile){
        ImageDiff differ = new ImageDiffer()
                .makeDiff(ImageIO.read(expectedFile), ImageIO.read(actualFile)) // Нахождение различий в скриншотах
                .withDiffSizeTrigger(10); // Погрешность
        if(differ.hasDiff()){
            BufferedImage diffImage = differ.getMarkedImage(); // Отметить изменения на скриншоте
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(diffImage, "png", bos);
            byte[] image = bos.toByteArray();
            Allure.getLifecycle().addAttachment("diff", "image/png", "png", image);
            Assertions.assertFalse(differ.hasDiff());
        }

    }
}
