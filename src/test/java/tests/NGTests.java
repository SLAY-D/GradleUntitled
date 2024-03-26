package tests;

import calc.CalcSteps;
import listener.RetryListenerTestNG;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(RetryListenerTestNG.class)
public class NGTests {
    @BeforeSuite // Каждый тест будет перезапускаться столько раз, сколько указано в RetryListenerTestNG()
    public void setAnalyzer(ITestContext context){
        for (ITestNGMethod testMethod : context.getAllTestMethods()) {
            testMethod.setRetryAnalyzer(new RetryListenerTestNG());
        }
    }

    @AfterSuite
    public void saveFailed(){
        RetryListenerTestNG.saveFailedTests();
    }

    @Test(groups = {"sum1"})
    public void sumTestNGTest(){
        CalcSteps calcSteps = new CalcSteps();
        Assert.assertTrue(calcSteps.isPositive(-10));
    }

    @Test(groups = {"sum2"})
    public void sumTestNGTest2(){
        CalcSteps calcSteps = new CalcSteps();
        Assert.assertTrue(calcSteps.isPositive(-20));
    }
}
