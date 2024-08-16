package tests.junit5.Selenoid;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;


@BrowserType(browser = BrowserType.Browser.CHROME, isRemote = false)
public class SelenoidChromeTests {


    @Test
    @BrowserType(browser = BrowserType.Browser.CHROME, isRemote = false)
    public void selenoidChromeTest(){
        Selenide.open("https://vk.com");
    }

    @Test
    @BrowserType(browser = BrowserType.Browser.FIREFOX, isRemote = true)
    public void selenoidFirefoxTest(){
        Selenide.open("https://vk.com");
    }
}
