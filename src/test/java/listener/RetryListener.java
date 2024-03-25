package listener;

import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/*
    Интерфейсы:
    TestExecutionExceptionHandler - Обрабатывает ошибки
    AfterTestExecutionCallback - Логика после завершения теста
 */

public class RetryListener implements TestExecutionExceptionHandler, AfterTestExecutionCallback {

    private static final int MAX_RETRIES = 3; // Количество повторений
    private static final Set<String> failedTestNames = new HashSet<>(); // Уникальные имена упавших тестов

    @Override
    public void handleTestExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
        for (int i = 0; i < MAX_RETRIES; i++) {
            try{
                context.getRequiredTestMethod().invoke(context.getRequiredTestInstance());
                return;
            }catch (Throwable e){
                throwable = e; // Обработка ошибки выполнения теста
            }
        }
        throw throwable; // В случае неуспеха выполнения теста показывается ошибка в логах

    }

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        Method method = context.getRequiredTestMethod();
        String testClass = context.getRequiredTestClass().getName();
        String testName = method.getName();
        String testToWrite = String.format("--tests %s.%s*" , testClass, testName);

        context.getExecutionException().ifPresent(ex->failedTestNames.add(testToWrite)); // Запись в сэт информации о перезапуске теста
    }

    @SneakyThrows
    public static void saveFailedTests() {
        String output = System.getProperty("user.dir") + "/src/test/resources/FailedTests.txt";
        String result = String.join(" ", failedTestNames);
        FileUtils.writeStringToFile(new File(output), result);
    }
}
