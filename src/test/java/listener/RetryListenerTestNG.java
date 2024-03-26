package listener;

import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.testng.IRetryAnalyzer;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class RetryListenerTestNG implements IRetryAnalyzer, ITestListener {
    private final int MAX_RETIES = 2;
    private int count = 0;
    private static final Set<String> failedTestNames = new HashSet<>(); // Уникальные имена упавших тестов


    @Override
    public boolean retry(ITestResult result) {
        if (count < MAX_RETIES){
            count++;
            return true;
        }
        return false;
    }

    private void addToFailedSet (ITestResult result){
        String testClass = result.getTestClass().getName();
        String testName = result.getName();
        String testToWrite = String.format("--tests %s.%s" , testClass, testName);
        failedTestNames.add(testToWrite);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        addToFailedSet(result);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        addToFailedSet(result);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        addToFailedSet(result);
    }

    @SneakyThrows
    public static void saveFailedTests() {
        String output = System.getProperty("user.dir") + "/src/test/resources/FailedTests.txt";
        String result = String.join(" ", failedTestNames);
        FileUtils.writeStringToFile(new File(output), result);
    }

    @Override
    public void onTestStart(ITestResult result) {

    }

    @Override
    public void onTestSuccess(ITestResult result) {

    }

    @Override
    public void onStart(ITestContext context) {

    }

    @Override
    public void onFinish(ITestContext context) {

    }
}
