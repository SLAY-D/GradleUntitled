package tests.swaggerTests;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import listener.AdminUser;
import listener.AdminUserResolver;
import listener.CustomTpl;
import models.Swagger.FullUser;
import models.Swagger.Responses.CreateUser.Info;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import services.UserService;

import java.util.List;
import java.util.Random;

import static assertions.Conditions.hasMessage;
import static assertions.Conditions.hasStatusCode;
import static utils.RandomTestData.*;

@ExtendWith(AdminUserResolver.class)
public class UserNewTests {

    private static UserService userService;
    private FullUser user;

    @BeforeEach
    public void initTestUser(){
        user = getRandomUser();
    }



    @BeforeAll
    public static void setUp(){
        RestAssured.baseURI = "http://85.192.34.140:8080/";
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter(), CustomTpl.customLogFilter().withCustomTemplate());
        userService = new UserService();
    }

    @Test
    public void positiveRegisterTest(){
        userService.register(user)
                .should(hasStatusCode(201))
                .should(hasMessage("User created"));
    }

    @Test
    public void positiveRegisterWithGamesTest(){
        FullUser user = getRandomUserWithGames();

        Response response = userService.register(user)
//                .should(hasStatusCode(201))
//                .should(hasMessage("User created"))
                .asResponse();

        Info info = response.jsonPath().getObject("info", Info.class);
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(info.getMessage()).as("Сообщение об ошибке было неверным")
                .isEqualTo("fake message");
        softAssertions.assertThat(response.statusCode()).as("Status code isnt 200")
                .isEqualTo(201);
        softAssertions.assertAll();
    }

    @Test
    public void negativeRegisterLoginExistTest(){
        userService.register(user);

        userService.register(user)
                .should(hasStatusCode(400))
                .should(hasMessage("Login already exist"));
    }

    @Test
    public void negativeNoPasswordTest(){
        user.setPass(null);
        userService.register(user)
                .should(hasStatusCode(400))
                .should(hasMessage("Missing login or password"));
    }

    @Test
    public void positiveAdminAuthTest(@AdminUser FullUser admin){
        String token = userService.auth(admin)
                .should(hasStatusCode(200))
                .asJwt();

        Assertions.assertNotNull(token);
    }

    @Test
    public void positiveNewUserAuthTest(){
        userService.register(user)
                        .should(hasStatusCode(201))
                        .should(hasMessage("User created"));

        String token = userService.auth(user)
                        .should(hasStatusCode(200))
                        .asJwt();

        Assertions.assertNotNull(token);
    }

    @Test
    public void negativeAuthTest(){
        userService.auth(user).should(hasStatusCode(401));
    }

    @Test
    public void positiveGetUserInfoTest(){
        FullUser user = getAdminUser();

        String token = userService.auth(user)
                .should(hasStatusCode(200))
                .asJwt();

        Assertions.assertNotNull(token);

        userService.getUserInfo(token)
                        .should(hasStatusCode(200));
    }

    @Test
    public void negativeGetUserInfoInvalidJWTTest(){
        userService.getUserInfo("fake jwt")
                        .should(hasStatusCode(401));
    }

    @Test
    public void negativeGetUserInfoWithoutJWTTest(){
        userService.getUserInfo()
                        .should(hasStatusCode(401));
    }

    // Проверка того, что пароль действительно изменился
    @Test
    public void positiveChangeUserPasswordTest(){
        String oldPass = user.getPass();
        userService.register(user)
                .should(hasStatusCode(201))
                .should(hasMessage("User created"));

        String token = userService.auth(user)
                .should(hasStatusCode(200))
                .asJwt();

        String updatedPassValue = "NewGribochekPass";
        userService.updatePass(updatedPassValue,token)
                .should(hasStatusCode(200))
                .should(hasMessage("User password successfully changed"));

        user.setPass(updatedPassValue);

        token = userService.auth(user)
                .should(hasStatusCode(200))
                .asJwt();

        FullUser updatedUser =  userService.getUserInfo(token).as(FullUser.class);

        Assertions.assertNotEquals(oldPass,updatedUser.getPass());
    }

    @Test
    public void negativeChangeAdminPasswordTest(){
        FullUser user = getAdminUser();

        String token = userService.auth(user)
                .should(hasStatusCode(200))
                .asJwt();

        String updatedPassValue = "NewGribochekPass";

        userService.updatePass(updatedPassValue, token)
                .should(hasStatusCode(400))
                .should(hasMessage("Cant update base users"));
    }

    @Test
    public void negativeDeleteAdminTest(){
        FullUser user = getAdminUser();

        String token = userService.auth(user)
                .should(hasStatusCode(200))
                .asJwt();

        userService.deleteUser(token)
                .should(hasStatusCode(400))
                .should(hasMessage("Cant delete base users"));
    }

    @Test
    public void positiveDeleteNewUserTest(){
        userService.register(user)
                .should(hasMessage("User created"))
                .should(hasStatusCode(201));

        String token = userService.auth(user)
                .should(hasStatusCode(200))
                .asJwt();

        userService.deleteUser(token)
                .should(hasStatusCode(200))
                .should(hasMessage("User successfully deleted"));
    }

    @Test
    public void positiveGetAllUsersTest(){
        List<String> userList = userService.getAllUsers().asList(String.class);
        Assertions.assertTrue(userList.size()>=3);
    }
}
