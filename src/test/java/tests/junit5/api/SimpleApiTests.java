package tests.junit5.api;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.FakeAPI.User.Address;
import models.FakeAPI.User.Geolocation;
import models.FakeAPI.User.Name;
import models.FakeAPI.User.UserRoot;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testng.Assert;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

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
                .get("/users/{userId}")
                .then()
                .log().all()
                .statusCode(200)
                .body("id",equalTo(userId))
                .body("address.zipcode", matchesPattern("\\d{5}-\\d{4}"));
    }

    @Test
    public void getAllUsersWithLimitTest(){
        int limitSize = 3;
        given().queryParam("limit",limitSize)
                .get("https://fakestoreapi.com/users")
                .then().log().all()
                .body("",hasSize(limitSize))
                .statusCode(200);
    }

    @Test
    public void getAllUsersSortByDesc(){
        String sortType = "desc";

        Response sortResponse = given().queryParam("sort",sortType)
                .get("https://fakestoreapi.com/users")
                .then().log().all()
                .extract().response();

        Response notSortResponse = given()
                .get("https://fakestoreapi.com/users")
                .then().log().all()
                .extract().response();

        List<Integer> sortedResponseId = sortResponse.jsonPath().getList("id");
        List<Integer> notSortedResponseId = notSortResponse.jsonPath().getList("id");
        List<Integer> sortedByCode = notSortedResponseId.stream()
                        .sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        System.out.println("Sorted: " + sortedResponseId);
        System.out.println("Not sorted: " + notSortedResponseId);
        Assertions.assertEquals(sortedByCode, sortedResponseId);
        Assertions.assertNotEquals(sortedResponseId, notSortedResponseId);
    }

    @Test
    public void addNewUserTest(){
        Name name = new Name("Albert", "Zasovich");
        Geolocation geolocation = new Geolocation("74129.372","719847.047");
        Address address = Address.builder()
                .city("Kemerovo")
                .number(7)
                .zipcode("34218-3245")
                .street("Lenina 35")
                .geolocation(geolocation)
                .build();
        UserRoot bodyRequest = UserRoot.builder()
                .name(name)
                .phone("89135673492")
                .email("spuki123@gmail.com")
                .username("slay")
                .password("qwerty123")
                .address(address)
                .build();

        given().body(bodyRequest)
                .post("https://fakestoreapi.com/users")
                .then().log().all()
                .statusCode(200)
                .body("id",notNullValue());
    }

    private UserRoot getTestUser(){
        Name name = new Name("Albert", "Zasovich");
        Geolocation geolocation = new Geolocation("74129.372","719847.047");
        Address address = Address.builder()
                .city("Kemerovo")
                .number(7)
                .zipcode("34218-3245")
                .street("Lenina 35")
                .geolocation(geolocation)
                .build();
        return UserRoot.builder()
                .name(name)
                .phone("89135673492")
                .email("spuki123@gmail.com")
                .username("slay")
                .password("qwerty123")
                .address(address)
                .build();
    }

    @Test
    public void updateUserTest(){
        UserRoot user = getTestUser();
        String oldPassword = user.getPassword();

        user.setPassword("newpassword!");
        given().body(user)
                .put("https://fakestoreapi.com/users/" + user.getId())
                .then().log().all()
                .body("password", not(equalTo(oldPassword)));
    }

    @Test
    public void deleteUserTest(){
        given().delete("https://fakestoreapi.com/users/8")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    public void authUserTest(){
        Map<String, String> userAuth = new HashMap<>();
        userAuth.put("username", "kate_h");
        userAuth.put("password", "kfejk@*_");
        String token = given().contentType(ContentType.JSON)
                .body(userAuth)
                .post("https://fakestoreapi.com/auth/login")
                .then().log().all()
                .statusCode(200)
                .body("token", notNullValue())
                .extract().path("token");
    }
}
