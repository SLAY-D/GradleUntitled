package tests.junit5.pageObjectTests.wildberries;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tests.junit5.pageObjectTests.wildberries.uniticketPages.UtMainPage;

public class UtFilterTest extends BaseTest{
    @BeforeEach
    public void openSite(){
        driver.get("https://uniticket.ru/");
    }

    @Test
    public void filterTest(){
        int expectedDayForward = 25;
        int expectedDayBack = 30;
        UtMainPage utMainPage = new UtMainPage(driver);
        utMainPage.setCityFrom("Казань")
                .setCityTo("Дубай")
                .setDateForward(expectedDayForward)
                .setDateBack(expectedDayBack)
                .search();
    }
}
