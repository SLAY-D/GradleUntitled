package tests.junit5.wildberries.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ItemPage extends BasePage{

    private By titleText = By.xpath("//h1[@class='product-page__title']");
    private By itemPriceText = By.xpath("(//span[@class='price-block__price'])[4]");


    public ItemPage(WebDriver driver) {
        super(driver);
    }

    public String getItemName(){
        return driver.findElement(titleText).getText();
    }

    public Integer getItemPrice(){
        /*  Инициализация JavascriptExecutor
            По большей части JS необходим тогда, когда Selenium не всегда может достать содержимое элемента
         */
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement itemPriceElement = driver.findElement(itemPriceText);

        // Скрипт возвращает содержимое текста элемента itemPriceElement
        String priceText = (String) js.executeScript("return arguments[0].textContent;", itemPriceElement);

        priceText = priceText.replaceAll("[^0-9.]","");
        return Integer.parseInt(priceText);
    }
}
