package tests.swaggerTests;

import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import listener.CustomTpl;
import models.Swagger.FullUser;
import models.Swagger.Requests.Login.JwtAuthData;
import models.Swagger.Responses.CreateUser.Info;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static io.restassured.RestAssured.*;


public class UserTests {

    private static Random random;

    @BeforeAll
    public static void setUp(){
        RestAssured.baseURI = "http://85.192.34.140:8080/";
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter(), CustomTpl.customLogFilter().withCustomTemplate());
        random = new Random();
    }

    @Test
    public void positiveRegisterTest(){

        int randomNumber = Math.abs(random.nextInt()); // Необходимо для того, чтобы всегда было уникальное имя пользователя

        FullUser user = FullUser.builder()
                .login("CombuchaUser" + randomNumber)
                .pass("gribochekPass")
                .build();
        
        Info info = given().contentType(ContentType.JSON)
                .body(user)
                .post("/api/signup")
                .then().statusCode(201)
                .extract().jsonPath().getObject("info", Info.class);

        Assertions.assertEquals("User created", info.getMessage());
    }

    @Test
    public void negativeRegisterLoginExistTest(){
        int randomNumber = Math.abs(random.nextInt()); // Необходимо для того, чтобы всегда было уникальное имя пользователя

        FullUser user = FullUser.builder()
                .login("CombuchaUser" + randomNumber)
                .pass("gribochekPass")
                .build();

        Info info = given().contentType(ContentType.JSON)
                .body(user)
                .post("/api/signup")
                .then().statusCode(201)
                .extract().jsonPath().getObject("info", Info.class);

        Assertions.assertEquals("User created", info.getMessage());

        Info errorInfo = given().contentType(ContentType.JSON)
                .body(user)
                .post("/api/signup")
                .then().statusCode(400)
                .extract().jsonPath().getObject("info", Info.class);

        Assertions.assertEquals("Login already exist", errorInfo.getMessage());
    }

    @Test
    public void negativeNoPasswordTest(){
        int randomNumber = Math.abs(random.nextInt()); // Необходимо для того, чтобы всегда было уникальное имя пользователя

        FullUser user = FullUser.builder()
                .login("CombuchaUser" + randomNumber)
                .build();

        Info errorInfo = given().contentType(ContentType.JSON)
                .body(user)
                .post("/api/signup")
                .then().statusCode(400)
                .extract().jsonPath().getObject("info", Info.class);

        Assertions.assertEquals("Missing login or password", errorInfo.getMessage());
    }

    @Test
    public void positiveAuthTest(){
        JwtAuthData authData = JwtAuthData.builder()
                .username("admin")
                .password("admin")
                .build();

        String token = given().contentType(ContentType.JSON)
                .body(authData)
                .post("/api/login")
                .then().statusCode(200)
                .extract().jsonPath().getString("token");

        Assertions.assertNotNull(token);
    }

    @Test
    public void positiveNewUserAuthTest(){
        int randomNumber = Math.abs(random.nextInt()); // Необходимо для того, чтобы всегда было уникальное имя пользователя

        FullUser user = FullUser.builder()
                .login("CombuchaUser" + randomNumber)
                .pass("gribochekPass")
                .build();

        Info info = given().contentType(ContentType.JSON)
                .body(user)
                .post("/api/signup")
                .then().statusCode(201)
                .extract().jsonPath().getObject("info", Info.class);

        Assertions.assertEquals("User created", info.getMessage());

        JwtAuthData authData = JwtAuthData.builder()
                .username(user.getLogin())
                .password(user.getPass())
                .build();

        String token = given().contentType(ContentType.JSON)
                .body(authData)
                .post("/api/login")
                .then().statusCode(200)
                .extract().jsonPath().getString("token");

        Assertions.assertNotNull(token);
    }

    @Test
    public void negativeAuthTest(){
        JwtAuthData authData = JwtAuthData.builder()
                .username("kvakozyabra")
                .password("temppass")
                .build();

        given().contentType(ContentType.JSON)
                .body(authData)
                .post("/api/login")
                .then().statusCode(401);
    }

    @Test
    public void positiveGetUserInfoTest(){
        JwtAuthData authData = JwtAuthData.builder()
                .username("admin")
                .password("admin")
                .build();

        String token = given().contentType(ContentType.JSON)
                .body(authData)
                .post("/api/login")
                .then().statusCode(200)
                .extract().jsonPath().getString("token");

        Assertions.assertNotNull(token);

        //given().header("Authorization", "Bearer " + token)
        given().auth().oauth2(token)
                .get("/api/user")
                .then().statusCode(200);

    }

    @Test
    public void negativeGetUserInfoInvalidJWTTest(){
        given().auth().oauth2("abeba")
                .get("/api/user")
                .then().statusCode(401);
    }

    @Test
    public void negativeGetUserInfoWithoutJWTTest(){
        given().get("/api/user")
                .then().statusCode(401);
    }

    // Проверка того, что пароль действительно изменился
    @Test
    public void positiveChangeUserPasswordTest(){
        int randomNumber = Math.abs(random.nextInt());

        FullUser user = FullUser.builder()
                .login("CombuchaUser" + randomNumber)
                .pass("gribochekPass")
                .build();

        Info info = given().contentType(ContentType.JSON)
                .body(user)
                .post("/api/signup")
                .then().statusCode(201)
                .extract().jsonPath().getObject("info", Info.class);

        Assertions.assertEquals("User created", info.getMessage());

        JwtAuthData authData = JwtAuthData.builder()
                .username(user.getLogin())
                .password(user.getPass())
                .build();

        String token = given().contentType(ContentType.JSON)
                .body(authData)
                .post("/api/login")
                .then().statusCode(200)
                .extract().jsonPath().getString("token");

        Map<String, String> password = new HashMap<>();
        String updatedPassValue = "NewGribochekPass";
        password.put("password", updatedPassValue);
        Info infoChangePass = given().auth().oauth2(token)
                .contentType(ContentType.JSON)
                .body(password)
                .put("/api/user")
                .then().extract().jsonPath().getObject("info",Info.class);

        Assertions.assertEquals("User password successfully changed", infoChangePass.getMessage());

        authData.setPassword(updatedPassValue);

        token = given().contentType(ContentType.JSON)
                .body(authData)
                .post("/api/login")
                .then().statusCode(200)
                .extract().jsonPath().getString("token");

        FullUser updatedUser = given().auth().oauth2(token)
                .get("/api/user")
                .then().statusCode(200)
                .extract().as(FullUser.class);
        Assertions.assertNotEquals(user.getPass(),updatedUser.getPass());
    }

    @Test
    public void negativeChangeAdminPasswordTest(){
        FullUser user = FullUser.builder()
                .login("admin")
                .pass("admin")
                .build();

        JwtAuthData authData = JwtAuthData.builder()
                .username(user.getLogin())
                .password(user.getPass())
                .build();

        String token = given().contentType(ContentType.JSON)
                .body(authData)
                .post("/api/login")
                .then().statusCode(200)
                .extract().jsonPath().getString("token");

        Map<String, String> password = new HashMap<>();
        String updatedPassValue = "NewGribochekPass";
        password.put("password", updatedPassValue);
        Info infoChangePass = given().auth().oauth2(token)
                .contentType(ContentType.JSON)
                .body(password)
                .put("/api/user")
                .then().statusCode(400)
                .extract().jsonPath().getObject("info",Info.class);

        Assertions.assertEquals("Cant update base users", infoChangePass.getMessage());
    }

    @Test
    public void negativeDeleteAdminTest(){
        FullUser user = FullUser.builder()
                .login("admin")
                .pass("admin")
                .build();

        JwtAuthData authData = JwtAuthData.builder()
                .username(user.getLogin())
                .password(user.getPass())
                .build();

        String token = given().contentType(ContentType.JSON)
                .body(authData)
                .post("/api/login")
                .then().statusCode(200)
                .extract().jsonPath().getString("token");

        Info infoDelete = given().auth().oauth2(token)
                .delete("/api/user")
                .then().statusCode(400)
                .extract().jsonPath().getObject("info",Info.class);

        Assertions.assertEquals("Cant delete base users", infoDelete.getMessage());
    }

    @Test
    public void positiveDeleteNewUserTest(){
        int randomNumber = Math.abs(random.nextInt());

        FullUser user = FullUser.builder()
                .login("CombuchaUser" + randomNumber)
                .pass("gribochekPass")
                .build();

        Info info = given().contentType(ContentType.JSON)
                .body(user)
                .post("/api/signup")
                .then().statusCode(201)
                .extract().jsonPath().getObject("info", Info.class);

        Assertions.assertEquals("User created", info.getMessage());

        JwtAuthData authData = JwtAuthData.builder()
                .username(user.getLogin())
                .password(user.getPass())
                .build();

        String token = given().contentType(ContentType.JSON)
                .body(authData)
                .post("/api/login")
                .then().statusCode(200)
                .extract().jsonPath().getString("token");

        Info infoDelete = given().auth().oauth2(token)
                .delete("/api/user")
                .then().statusCode(200)
                .extract().jsonPath().getObject("info",Info.class);

        Assertions.assertEquals("User successfully deleted", infoDelete.getMessage());
    }

    @Test
    public void positiveGetAllUsersTest(){
        List<String> userList = given()
                .get("/api/users")
                .then()
                .statusCode(200)
                .extract().as(new TypeRef<List<String>>() {});
        Assertions.assertTrue(userList.size()>=3);
    }
}
