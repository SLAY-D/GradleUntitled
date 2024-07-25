package tests.junit5.pageObjectTests.wildberries;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/*
    Класс относится ко всему проекту WB.
    Здесь находятся универсальные методы, которые подходят под ситуации на всех страницах.
 */

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected JavascriptExecutor js;
    protected Actions actions;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        /*  Инициализация JavascriptExecutor
            По большей части JS необходим тогда, когда Selenium не всегда может достать содержимое элемента
         */
        js = (JavascriptExecutor) driver;
        actions = new Actions(driver);
    }

    public String getTextJs(By element){
        return (String) js.executeScript("return arguments[0].textContent;", driver.findElement(element));
    }

    public void clickJs(By element){
        js.executeScript("arguments[0].click;", driver.findElement(element));
    }

    // Ожидание на лоадер на странице
    public void waitPageLoadWb(){
        By pageLoader = By.xpath("//div[@class='general-preloader']");
        wait.until(ExpectedConditions.invisibilityOfElementLocated(pageLoader)); // Ожидание когда пропадет элемент
    }

    public void waitForElementUpdated(By locator){
        wait.until(ExpectedConditions.stalenessOf(driver.findElement(locator))); // Ожидание пока появится элемент
    }

    public void clearTextField(By locator){
        driver.findElement(locator).clear();
        driver.findElement(locator).sendKeys(Keys.LEFT_CONTROL + "A");
        driver.findElement(locator).sendKeys(Keys.BACK_SPACE);
    }

    public WebElement waitForTextPresentedInList(By list, String value){
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(list)); // Ожидание всех элементов списка
        //wait.until(ExpectedConditions.elementToBeClickable(list)); // Ожидание всех элементов списка
        return driver.findElements(list).stream()
                .filter(x->x.getText().contains(value))
                .findFirst()
                .orElseThrow(()->new NoSuchElementException("Такого города нет " + value));
    }

    public void waitForTextMatchesRegex(By locator, String regex){
        Pattern pattern = Pattern.compile(regex);
        wait.until(ExpectedConditions.textMatches(locator, pattern)); // Ждем пока текст не будет совпадать с регуляркой
    }

    public void waitForElementDisappear(By locator){
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public void waitForElementAppear(By locator){
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public Integer getDigitFromWebElement(WebElement element){
        Integer digit = Integer.valueOf(element.getText().replaceAll("[^0-9.]",""));
        return digit;
        // Переписать? 37m
    }

    public List<Integer> getDigitsFromList(By locator){
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
        return driver.findElements(locator).stream()
                .filter(x->x.isDisplayed())
                .map(x->getDigitFromWebElement(x))
                .collect(Collectors.toList());
    }



}
