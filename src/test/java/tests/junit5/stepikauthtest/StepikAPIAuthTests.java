package tests.junit5.stepikauthtest;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;

import java.util.*;

import static com.codeborne.selenide.Selenide.$x;
import static io.restassured.RestAssured.given;

public class StepikAPIAuthTests {

    private StepikAuth testUser;

    @BeforeEach
    public void init(){
        Selenide.open("https://stepik.org/catalog");
        testUser = new StepikAuth("fetefo6842@acpeak.com", "qwerty123!");
    }

    @Test
    public void checkUserName(){
        authAPI(testUser);
        $x("//img[@class='navbar__profile-img']").should(Condition.visible).click();
        $x("//li[@data-qa='menu-item-profile']").should(Condition.visible).click();
        $x("//h1[@class='profile__title']").should(Condition.text("Temp temp"));
    }

    private void authAPI(StepikAuth user){
        Set<Cookie> cookiesBrowser = WebDriverRunner.getWebDriver().manage().getCookies();
        Map<String, String> authHeaders = new HashMap<>();
        authHeaders.put("referer", "https://stepik.org/catalog?auth=login");
        authHeaders.put("origin", "https://stepik.org");
        authHeaders.put("authority", "stepik.org");

        List<io.restassured.http.Cookie> restAssuredCookie = new ArrayList<>();
        for (Cookie cookie : cookiesBrowser) { // Перебор полученных кук и подстановка их в новые куки
            io.restassured.http.Cookie temp = new io.restassured.http.Cookie
                    .Builder(cookie.getName(), cookie.getValue())
                    .setDomain(cookie.getDomain())
                    .setPath("/").build();
            restAssuredCookie.add(temp);
            if(cookie.getName().equals("csrftoken")){
                authHeaders.put("X-csrftoken", cookie.getValue()); // Помещение куки в заголовок запроса
            }
        }

        Map<String, String> authCookies = given().contentType(ContentType.JSON)
                .body(user)
                .headers(authHeaders)
                .cookies(new Cookies(restAssuredCookie))
                .post("https://stepik.org/api/users/login")
                .then().log().all().extract().cookies();

        Cookie csrf = new Cookie("csrftoken", authCookies.get("csrftoken"), "stepik.org", "/", null);
        Cookie sessionId = new Cookie("sessionid", authCookies.get("sessionid"), "stepik.org", "/", null);

        WebDriverRunner.getWebDriver().manage().addCookie(csrf);
        WebDriverRunner.getWebDriver().manage().addCookie(sessionId);
        Selenide.refresh();
    }

}
