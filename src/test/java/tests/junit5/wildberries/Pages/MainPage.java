package tests.junit5.wildberries.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

public class MainPage extends BasePage{

    private final By searchField = By.id("searchInput");
    private final By searchBtn = By.id("applySearchBtn");
    private final By cartBtn = By.xpath("//a[@data-wba-header-name='Cart']");
    private final By profileBtn = By.xpath("//a[@data-wba-header-name='LK']");

    public MainPage(WebDriver driver) {
        super(driver);
        waitPageLoad();
    }

    /*
        Конструкция в виде public SearchResultPage необходима для того,
        чтобы в основном классе теста не делать объявление нового драйвера для каждого экрана.
        Таким образом после выполнения функции у нас автоматически создается новый экземпляр класса с новым экраном.
     */
    public SearchResultPage searchItem(String item){
        driver.findElement(searchField).click();
        driver.findElement(searchField).sendKeys(item);
        //driver.findElement(searchBtn).click();
        driver.findElement(searchField).sendKeys(Keys.ENTER);
        return new SearchResultPage(driver);
    }
}
