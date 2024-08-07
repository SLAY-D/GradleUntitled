package tests.junit5.pageObjectTests.wildberries.wbPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import tests.junit5.pageObjectTests.wildberries.BasePage;

public class ItemPage extends BasePage {

    private By titleText = By.xpath("//h1[@class='product-page__title']");
    private By itemPriceText = By.xpath("(//span[@class='price-block__price'])[4]");


    public ItemPage(WebDriver driver) {
        super(driver);
    }

    public String getItemName(){
        return driver.findElement(titleText).getText();
    }

    public Integer getItemPrice(){

        // Скрипт возвращает содержимое текста элемента itemPriceElement
        String priceText = getTextJs(itemPriceText);

        priceText = priceText.replaceAll("[^0-9.]","");
        return Integer.parseInt(priceText);
    }
}
