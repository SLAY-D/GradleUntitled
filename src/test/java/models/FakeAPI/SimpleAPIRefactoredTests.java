package models.FakeAPI;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import listener.CustomTpl;
import lombok.Value;
import models.FakeAPI.User.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.*;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/*
        Добавлен класс java/models/FakeAPI/SimpleAPIRefactoredTests.java
        для упрощения запросов RestAssured

        Также добавлен фильтр AllureRestAssured(), который отображает в Allure отчете подробно каждый запрос по тесту
*/

public class SimpleAPIRefactoredTests {
    @BeforeAll
    public static void setUp(){
        RestAssured.baseURI = "https://fakestoreapi.com";
        // RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter(), new AllureRestAssured());
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter(), CustomTpl.customLogFilter().withCustomTemplate());
    }

    @Test
    public void getAllUsersTest(){
        given().get("/users")
                .then()
                .statusCode(200);
    }

    @Test
    public void getSingleUserTest(){
        int userId = 2;
        UserRoot response = given().pathParam("userId", userId)
                .get("/users/{userId}")
                .then()
                .statusCode(200)
                .extract().as(UserRoot.class);

//        Name name = given().pathParam("userId", userId)
//                .get("/users/{userId}")
//                .then()
//                .extract().jsonPath().getObject("name", Name.class);

        Assertions.assertEquals(userId, response.getId());
        Assertions.assertTrue(response.getAddress().getZipcode().matches("\\d{5}-\\d{4}"));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 10, 20})
    public void getAllUsersWithLimitTest(int limitSize){
        List<UserRoot> users = given().queryParam("limit",limitSize)
                .get("/users")
                .then()
                .statusCode(200)
                //.extract().jsonPath().getList("",UserRoot.class);
                .extract().as(new TypeRef<List<UserRoot>>() {});
        Assertions.assertEquals(limitSize,users.size());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 40})
    public void getAllUsersWithErrorParamsTest(int limitSize){
        List<UserRoot> users = given().queryParam("limit",limitSize)
                .get("/users")
                .then()
                .statusCode(200)
                .extract().as(new TypeRef<List<UserRoot>>() {});
        Assertions.assertNotEquals(limitSize, users.size());

    }

    @Test
    public void getAllUsersSortByDesc(){
        String sortType = "desc";

        List<UserRoot> usersSorted = given()
                .queryParam("sort",sortType)
                .get("/users")
                .then()
                .extract().as(new TypeRef<List<UserRoot>>() {});

        List<UserRoot> usersNotSorted = given()
                .get("/users")
                .then()
                .extract().as(new TypeRef<List<UserRoot>>() {});

        List<Integer> sortedResponseId = usersSorted.stream()
                .map(UserRoot::getId).collect(Collectors.toList());

        List<Integer> sortedByCode = usersNotSorted.stream()
                .map(UserRoot::getId)
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());

        Assertions.assertNotEquals(usersSorted, usersNotSorted);
        Assertions.assertEquals(sortedResponseId, sortedByCode);
    }

    @Test
    public void addNewUserTest(){
        UserRoot user = getTestUser();

        Integer userId = given().body(user)
                .post("/users")
                .then()
                .statusCode(200)
                .extract().jsonPath().getInt("id");

        Assertions.assertNotNull(userId);
    }

    @Test
    public void updateUserTest(){
        UserRoot user = getTestUser();
        String oldPassword = user.getPassword();
        System.out.println(user.getId());
        user.setPassword("newpassword!");
        UserRoot updatedUser = given()
                .body(user)
                .pathParam("userId", user.getId())
                .put("/users/{userId}")
                .then()
                .extract().as(UserRoot.class);
        Assertions.assertNotEquals(updatedUser.getPassword(),oldPassword);
    }

    private UserRoot getTestUser(){
        Random random = new Random();
        Name name = new Name("Albert", "Zasovich");
        Geolocation geolocation = new Geolocation("74129.372","719847.047");
        Address address = Address.builder()
                .city("Kemerovo")
                .number(random.nextInt(100))
                .zipcode("34218-3245")
                .street("Lenina 35")
                .geolocation(geolocation)
                .build();
        return UserRoot.builder()
                .id(5)
                .name(name)
                .phone("89135673492")
                .email("spuki123@gmail.com")
                .username("slay")
                .password("qwerty123")
                .address(address)
                .build();
    }

    @Test
    public void authUserTest(){
        AuthData authdata = new AuthData("mor_2314","83r5^_");

        String token = given().contentType(ContentType.JSON)
                .body(authdata)
                .post("/auth/login")
                .then()
                .statusCode(200)
                .body("token", notNullValue())
                .extract().path("token");
        Assertions.assertNotNull(token);
        System.out.println(token);
    }
}
