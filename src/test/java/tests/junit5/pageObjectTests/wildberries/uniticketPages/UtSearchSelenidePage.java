package tests.junit5.pageObjectTests.wildberries.uniticketPages;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import java.time.Duration;
import java.util.List;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class UtSearchSelenidePage {
    private SelenideElement titleLoader = $x("//div[@class='countdown-title']");
    private SelenideElement priceSelectedMain = $x("//li[@class='price--current']//span[@class='prices__price currency_font currency_font--rub']");
    private SelenideElement selectedDayForward = $x("//li[@class='price--current']//span[@class='prices__date'][1]");
    private SelenideElement selectedDayBack = $x("//li[@class='price--current']//span[@class='prices__date'][2]");
    private ElementsCollection listOfForwardDays = $$x("//div[@class='ticket-action-airline-container']//following::span[@class='flight-brief-date__day'][1]");
    private ElementsCollection listOfBackDays = $$x("//div[@class='ticket-action-airline-container']//following::span[@class='flight-brief-date__day'][3]");


    public UtSearchSelenidePage assertAllDaysForwardShouldHaveDay(int expectedForwardDay){
        String day = String.valueOf(expectedForwardDay);
        listOfForwardDays.should(CollectionCondition.containExactTextsCaseSensitive(day));
        return this;
    }

    public UtSearchSelenidePage assertAllDaysBackShouldHaveDay(int expectedBackDay){
        String day = String.valueOf(expectedBackDay);
        listOfBackDays.should(CollectionCondition.containExactTextsCaseSensitive(day));
        return this;
    }

    public UtSearchSelenidePage assertMainDayForward(int expectedDayForward){
        selectedDayForward.should(Condition.partialText(String.valueOf(expectedDayForward)));
        return this;
    }

    public UtSearchSelenidePage assertMainDayBack(int expectedDayBack){
        selectedDayBack.should(Condition.partialText(String.valueOf(expectedDayBack)));
        return this;
    }

    public UtSearchSelenidePage waitForPage(){
        priceSelectedMain.should(Condition.matchText("\\d+"));
        return this;
    }

    public UtSearchSelenidePage waitForTitleDisappear(){
        titleLoader.should(Condition.disappear, Duration.ofSeconds(30));
        return this;
    }

}
