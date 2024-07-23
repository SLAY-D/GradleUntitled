package tests.junit5.pageObjectTests.wildberries.uniticketPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import tests.junit5.pageObjectTests.wildberries.BasePage;

public class UtMainPage extends BasePage {

    private By cityFromField = By.xpath("//input[@placeholder='Откуда']");
    private By listCityFrom = By.xpath("//div[@class='origin field active']//div[@class='city']");

    private By cityToField = By.xpath("//input[@placeholder='Куда']");
    private By listCityTo = By.xpath("//div[@class='destination field active']//div[@class='city']");

    private By dateForward = By.xpath("//input[@placeholder='Туда']");
    private By dateBack = By.xpath("//input[@placeholder='Обратно']");

    private String dayInCalendar = "//span[text()='%d']";
    private By searchBtn = By.xpath("//div[@class='search_btn']");

    public UtMainPage(WebDriver driver) {
        super(driver);
        wait.until(ExpectedConditions.presenceOfElementLocated(cityFromField));
        wait.until(ExpectedConditions.elementToBeClickable(searchBtn)); // Ожидание того момента, когда кнопка станет активной
    }

    public UtMainPage setCityFrom(String city){
        driver.findElement(cityFromField).clear();
        driver.findElement(cityFromField).sendKeys(city);
        driver.findElement(cityFromField).click();
        waitForTextPresentedInList(listCityFrom, city).click();
        return this;
    }

    public UtMainPage setCityTo(String city){
        driver.findElement(cityToField).clear();
        driver.findElement(cityToField).sendKeys(city);
        driver.findElement(cityToField).click();
        waitForTextPresentedInList(listCityTo, city).click();
        return this;
    }

    public UtMainPage setDateForward(int day){
        driver.findElement(dateForward).click();
        //wait.until(ExpectedConditions.presenceOfElementLocated((By) getDay(day)));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@class='month']")));
        System.out.println(getDay(day));
        getDay(day).click();
        return this;
    }

    public UtMainPage setDateBack(int day){
        driver.findElement(dateBack).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@class='month']")));
        getDay(day).click();
        return this;
    }

    public void search(){
        driver.findElement(searchBtn).click();
    }

    private WebElement getDay(int day){
        By dayLocator = By.xpath(String.format(dayInCalendar, day)); // Преобразование полученного числа в элемент с датой
        return driver.findElement(dayLocator);
    }
}
