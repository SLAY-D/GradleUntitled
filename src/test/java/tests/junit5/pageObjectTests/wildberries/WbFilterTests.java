package tests.junit5.pageObjectTests.wildberries;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tests.junit5.pageObjectTests.wildberries.wbPages.ItemPage;
import tests.junit5.pageObjectTests.wildberries.wbPages.MainPage;

public class WbFilterTests extends BaseTest{

    @BeforeEach
    public void openSite(){
        driver.get("https://www.wildberries.ru/");
    }

    @Test
    public void searchResultTests(){
        String expectedItem = "iPhone";
        Integer expectedPriceMax = 60000;
        Integer expectedPriceMin = 30000;

        ItemPage itemPage = new MainPage(driver)
                .searchItem(expectedItem)
                .openFilters()
                .setMinPrice(expectedPriceMin)
                .setMaxPrice(expectedPriceMax)
                .applyFilters()
                .openItem();

        String actualName = itemPage.getItemName();
        Integer actualPrice = itemPage.getItemPrice();
        Assertions.assertTrue(actualName.toLowerCase().contains(expectedItem.toLowerCase()));
        System.out.println(actualPrice);
        Assertions.assertTrue(actualPrice >= expectedPriceMin && actualPrice <= expectedPriceMax);
    }
}
