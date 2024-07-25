package tests.junit5.pageObjectTests.wildberries;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tests.junit5.pageObjectTests.wildberries.uniticketPages.UtMainPage;
import tests.junit5.pageObjectTests.wildberries.uniticketPages.UtSearchPage;

import java.util.List;

public class UtFilterTest extends BaseTest{
    @BeforeEach
    public void openSite(){
        driver.get("https://uniticket.ru/");
    }

    @Test
    public void filterTest(){
        int expectedDayForward = 25;
        int expectedDayBack = 30;
        UtSearchPage utSearchPage = new UtMainPage(driver).setCityFrom("Казань")
                .setCityTo("Дубай")
                .setDateForward(expectedDayForward)
                .setDateBack(expectedDayBack)
                .search();

        int actualDateForward = utSearchPage.getMainDayForward();
        int actualDateBack = utSearchPage.getMainDayBack();
        Assertions.assertEquals(expectedDayForward, actualDateForward);
        Assertions.assertEquals(expectedDayBack, actualDateBack);

        List<Integer> daysForward = utSearchPage.getDaysForward();
        List<Integer> daysBack = utSearchPage.getDaysBack();

        boolean isAllDaysForwardOk = daysForward.stream().allMatch(x->x.equals(expectedDayForward));
        boolean isAllDaysBackOk = daysBack.stream().allMatch(x->x.equals(expectedDayBack));

        Assertions.assertTrue(isAllDaysForwardOk);
        Assertions.assertTrue(isAllDaysBackOk);
    }
}
