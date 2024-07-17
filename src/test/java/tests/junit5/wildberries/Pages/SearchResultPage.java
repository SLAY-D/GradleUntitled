package tests.junit5.wildberries.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

public class SearchResultPage {
    private WebDriver driver;

    private By allFilterBtn = By.xpath("//button[@class='dropdown-filter__btn dropdown-filter__btn--all']");
    private By endPriceField = By.xpath("//input[@class='j-price' and @name='endN']");
    private By startPriceField = By.xpath("//input[@class='j-price' and @name='startN']");
    private By applyBtn = By.xpath("//button[@class='filters-desktop__btn-main btn-main']");
    private By items = By.xpath("//div[@class='product-card-list']//article");


    public SearchResultPage(WebDriver driver) {
        this.driver = driver;
    }

    public void openFilters(){
        driver.findElement(allFilterBtn).click();
    }

    public void setMinPrice(Integer minValue){
        driver.findElement(startPriceField).clear();
        driver.findElement(startPriceField).sendKeys(String.valueOf(minValue));
    }

    public void setMaxPrice(Integer maxValue){
        driver.findElement(endPriceField).clear();
        driver.findElement(endPriceField).sendKeys(Keys.LEFT_CONTROL + "A");
        driver.findElement(endPriceField).sendKeys(Keys.BACK_SPACE);
        driver.findElement(endPriceField).sendKeys(String.valueOf(maxValue));
    }

    public void applyFilters(){
        driver.findElement(applyBtn).click();
    }

    public void openItem(){
        driver.findElements(items).get(0).click();
//        driver.findElements(items).stream()
//                .filter(x->x.getText().contains("iPhone 11"))
//                .findAny()
//                .orElseThrow(()->new NoSuchElementException("Товар не найден"))
//                .click();
    }
}
