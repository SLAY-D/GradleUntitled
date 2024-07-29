package tests.junit5.pageObjectTests.wildberries;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import tests.junit5.pageObjectTests.wildberries.uniticketPages.UtMainSelenidePage;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class UtSelenideTest {

    @Test
    public void firstSelenideTest(){
        int expectedDayForward = 29;
        int expectedDayBack = 30;

        Selenide.open("https://uniticket.ru/");
        UtMainSelenidePage mainPage = new UtMainSelenidePage();
        mainPage.setCityFrom("Казань")
                .setCityTo("Дубай")
                .setDateForward(expectedDayForward)
                .setDateBack(expectedDayBack)
                .search()
                .waitForPage()
                .waitForTitleDisappear()
                .assertMainDayForward(expectedDayForward)
                .assertMainDayBack(expectedDayBack)
                .assertAllDaysForwardShouldHaveDay(expectedDayForward)
                .assertAllDaysBackShouldHaveDay(expectedDayBack);
    }


}
