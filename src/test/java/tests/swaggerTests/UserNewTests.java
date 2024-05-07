package tests.swaggerTests;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import listener.CustomTpl;
import models.Swagger.FullUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import services.UserService;

import java.util.List;
import java.util.Random;

import static assertions.Conditions.hasMessage;
import static assertions.Conditions.hasStatusCode;
import static utils.RandomTestData.*;

public class UserNewTests {

    private static UserService userService;

    @BeforeAll
    public static void setUp(){
        RestAssured.baseURI = "http://85.192.34.140:8080/";
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter(), CustomTpl.customLogFilter().withCustomTemplate());
        userService = new UserService();
    }


    @Test
    public void positiveRegisterTest(){
        FullUser user = getRandomUser();

        userService.register(user)
                .should(hasStatusCode(201))
                .should(hasMessage("User created"));
    }

    @Test
    public void positiveRegisterWithGameTest(){
        FullUser user = getRandomUserWithGames();

        userService.register(user)
                .should(hasStatusCode(201))
                .should(hasMessage("User created"));
    }

    @Test
    public void negativeRegisterLoginExistTest(){
        FullUser user = getRandomUser();
        userService.register(user);

        userService.register(user)
                .should(hasStatusCode(400))
                .should(hasMessage("Login already exist"));
    }

    @Test
    public void negativeNoPasswordTest(){
        FullUser user = getRandomUser();
        user.setPass(null);
        userService.register(user)
                .should(hasStatusCode(400))
                .should(hasMessage("Missing login or password"));
    }

    @Test
    public void positiveAuthTest(){
        FullUser user = getAdminUser();
        String token = userService.auth(user)
                .should(hasStatusCode(200))
                .asJwt();

        Assertions.assertNotNull(token);
    }

    @Test
    public void positiveNewUserAuthTest(){
        FullUser user = getRandomUser();
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
        FullUser user = getRandomUser();
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
        FullUser user = getRandomUser();
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
        FullUser user = getRandomUser();

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
