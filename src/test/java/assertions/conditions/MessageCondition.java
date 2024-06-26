package assertions.conditions;

import assertions.Condition;
import io.restassured.response.ValidatableResponse;
import lombok.RequiredArgsConstructor;
import models.Swagger.Responses.CreateUser.Info;
import org.junit.jupiter.api.Assertions;

import static org.hamcrest.Matchers.equalTo;

@RequiredArgsConstructor
public class MessageCondition implements Condition {

    private final String expectedMessage;

    // Извлечение и проверка поля info в ответе
    @Override
    public void check(ValidatableResponse response) {
        Info info = response.extract().jsonPath().getObject("info", Info.class);
        Assertions.assertEquals(expectedMessage,info.getMessage());

        //response.body("info.message", equalTo(expectedMessage));
    }
}
