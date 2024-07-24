package tests.junit5.pageObjectTests.wildberries.uniticketPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import tests.junit5.pageObjectTests.wildberries.BasePage;

public class UtSearchPage extends BasePage {

    private By titleLoader = By.xpath("//div[@class='countdown-title']");
    private By priceSelectedMain = By.xpath("//li[@class='price--current']//span[@class='prices__price currency_font currency_font--rub']");
    private By selectedDayForward = By.xpath("//li[@class='price--current']//span[@class='prices__date'][1]");
    private By selectedDayBack = By.xpath("//li[@class='price--current']//span[@class='prices__date'][2]");
    private By listOfForwardDays = By.xpath("//div[@class='ticket-action-airline-container']//following::span[@class='flight-brief-date__day'][1]");
    private By listOfBackDays = By.xpath("//div[@class='ticket-action-airline-container']//following::span[@class='flight-brief-date__day'][3]");

    public UtSearchPage(WebDriver driver) {
        super(driver);
    }
}
