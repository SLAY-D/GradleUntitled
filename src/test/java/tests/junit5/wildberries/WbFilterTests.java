package tests.junit5.wildberries;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tests.junit5.wildberries.Pages.ItemPage;
import tests.junit5.wildberries.Pages.MainPage;
import tests.junit5.wildberries.Pages.SearchResultPage;

public class WbFilterTests extends BaseTest{

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
