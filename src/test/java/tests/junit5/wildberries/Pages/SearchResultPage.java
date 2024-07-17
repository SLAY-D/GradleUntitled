package tests.junit5.wildberries.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

public class SearchResultPage extends BasePage{

    private By allFilterBtn = By.xpath("//button[@class='dropdown-filter__btn dropdown-filter__btn--all']");
    private By endPriceField = By.xpath("//input[@class='j-price' and @name='endN']");
    private By startPriceField = By.xpath("//input[@class='j-price' and @name='startN']");
    private By applyBtn = By.xpath("//button[@class='filters-desktop__btn-main btn-main']");
    private By items = By.xpath("//div[@class='product-card-list']//article");


    public SearchResultPage(WebDriver driver) {
        super(driver);
    }

    /*
        В классе SearchResultPage мы объявляем методы не через void, а через название класса.
        С помощью такого объявления можно вызывать цепочку методов. Данное применение видно в WbFilterTests в методе searchResultTests.
     */
    public SearchResultPage openFilters(){
        driver.findElement(allFilterBtn).click();
        return this;
    }

    public SearchResultPage setMinPrice(Integer minValue){
        driver.findElement(startPriceField).clear();
        driver.findElement(startPriceField).sendKeys(String.valueOf(minValue));
        return this;
    }

    public SearchResultPage setMaxPrice(Integer maxValue){
        clearTextField(endPriceField);
        driver.findElement(endPriceField).sendKeys(String.valueOf(maxValue));
        return this;
    }

    public SearchResultPage applyFilters(){
        driver.findElement(applyBtn).click();
        waitForElementUpdated(items);
        return this;
    }

    public ItemPage openItem(){
        driver.findElements(items).get(0).click();
        waitPageLoad();
        return new ItemPage(driver);
//        driver.findElements(items).stream()
//                .filter(x->x.getText().contains("iPhone 11"))
//                .findAny()
//                .orElseThrow(()->new NoSuchElementException("Товар не найден"))
//                .click();
    }
}
