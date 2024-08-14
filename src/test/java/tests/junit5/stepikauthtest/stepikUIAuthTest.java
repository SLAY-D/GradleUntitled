package tests.junit5.stepikauthtest;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$x;

public class stepikUIAuthTest {

    private String login = "fetefo6842@acpeak.com";
    private String password = "qwerty123!";

    @Test
    public void UIAuthTest(){
        Selenide.open("https://stepik.org/catalog");
        $x("//a[contains(@class,'navbar__auth_login ')]").click();
        $x("//input[@name='login']").sendKeys(login);
        $x("//input[@name='password']").sendKeys(password);
        $x("//button[@type='submit']").click();
        $x("//img[@class='navbar__profile-img']").should(Condition.visible).click();
        $x("//li[@data-qa='menu-item-profile']").should(Condition.visible).click();
        $x("//h1[@class='profile__title']").should(Condition.text("Temp temp"));
    }
}
