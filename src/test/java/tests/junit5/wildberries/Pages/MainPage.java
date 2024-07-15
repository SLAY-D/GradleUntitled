package tests.junit5.wildberries.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

public class MainPage {
    private WebDriver driver;

    private final By searchField = By.id("searchInput");
    private final By searchBtn = By.id("applySearchBtn");
    private final By cartBtn = By.xpath("//a[@data-wba-header-name='Cart']");
    private final By profileBtn = By.xpath("//a[@data-wba-header-name='LK']");

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    public void searchItem(String item){
        driver.findElement(searchField).click();
        driver.findElement(searchField).sendKeys(item);
        //driver.findElement(searchBtn).click();
        driver.findElement(searchField).sendKeys(Keys.ENTER);
    }
}
