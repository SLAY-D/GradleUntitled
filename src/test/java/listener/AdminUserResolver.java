package listener;

import models.Swagger.FullUser;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

// Позволяет читать аннотацию AdminUser
public class AdminUserResolver implements ParameterResolver {

    // Описание поддерживаемых аннотаций
    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.isAnnotated(AdminUser.class);
    }

    // Выбираем какой элемент возвращать когда подставляем аннотацию
    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        Class<?> type = parameterContext.getParameter().getType();
        if (FullUser.class.equals(type)){
            return FullUser.builder()
                    .login("admin")
                    .pass("admin")
                    .build();
        }

        throw new ParameterResolutionException("Администратор не сгенерирован");
    }
}
