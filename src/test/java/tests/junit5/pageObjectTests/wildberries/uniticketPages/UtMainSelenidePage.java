package tests.junit5.pageObjectTests.wildberries.uniticketPages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;

public class UtMainSelenidePage {
    private SelenideElement cityFromField = $x("//input[@placeholder='Откуда']");
    private ElementsCollection listCityFrom = $$x("//div[@class='origin field active']//div[@class='city']");

    private SelenideElement cityToField = $x("//input[@placeholder='Куда']");
    private ElementsCollection listCityTo = $$x("//div[@class='destination field active']//div[@class='city']");

    private SelenideElement dateForward = $x("//input[@placeholder='Туда']");
    private SelenideElement dateBack = $x("//input[@placeholder='Обратно']");

    private String dayInCalendar = "//span[text()='%d']";
    private SelenideElement searchBtn = $x("//div[@class='search_btn']");

    public UtMainSelenidePage setCityFrom(String city){
        cityFromField.clear();
        cityFromField.sendKeys(city);
        cityFromField.click();
        listCityFrom.find(Condition.partialText(city)).click();
        return this;
    }

    public UtMainSelenidePage setCityTo(String city){
        cityToField.clear();
        cityToField.sendKeys(city);
        cityToField.click();
        listCityTo.find(Condition.partialText(city)).click();
        return this;
    }

    public UtMainSelenidePage setDateForward(int day){
        dateForward.click();
        getDay(day).click();
        $x("//h1").click();
        return this;
    }

    public UtMainSelenidePage setDateBack(int day){
        dateBack.click();
        getDay(day).click();
        $x("//h1").click();
        return this;
    }

    private SelenideElement getDay(int day){
        return $x(String.format(dayInCalendar, day)); // Преобразование полученного числа в элемент с датой
    }

    public UtSearchSelenidePage search(){
        searchBtn.click();
        return page(UtSearchSelenidePage.class);
    }
}
