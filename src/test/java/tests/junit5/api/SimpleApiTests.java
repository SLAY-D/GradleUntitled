package tests.junit5.api;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesPattern;

public class SimpleApiTests {

    /*
        contentType - Формат входных данных
        then() - Дальнейшая обработка запроса
        jsonPath() - Путь JSON для получение определенного поля

     */

    @Test
    public void getAllUsersTest(){
        given().get("https://fakestoreapi.com/users")
                .then()
                .log().all()
                .statusCode(200);
    }

    // Подставление параметра внутрь запроса
    @Test
    public void getSingleUserTest(){
        int userId = 2;
        given().pathParam("userId", userId)
                .get("https://fakestoreapi.com/users/{userId}")
                .then()
                .log().all()
                .statusCode(200)
                .body("id",equalTo(userId))
                .body("address.zipcode", matchesPattern("\\d{5}-\\d{4}"));
    }
}
