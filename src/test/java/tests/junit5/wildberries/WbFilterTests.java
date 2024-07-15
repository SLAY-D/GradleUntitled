package tests.junit5.wildberries;

import org.junit.jupiter.api.Test;
import tests.junit5.wildberries.Pages.MainPage;

public class WbFilterTests extends BaseTest{

    @Test
    public void searchResultTests(){
        String expectedItem = "Бензопила";
        MainPage mainPage = new MainPage(driver);
        mainPage.searchItem(expectedItem);
    }
}
