package tests;

import calc.CalcSteps;
import listener.RetryListenerTestNG;
import org.junit.jupiter.api.Assertions;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.annotations.*;
import tests.models.People;

import java.util.ArrayList;
import java.util.List;

@Listeners(RetryListenerTestNG.class)
public class NGTests {
    @BeforeSuite // Каждый тест будет перезапускаться столько раз, сколько указано в RetryListenerTestNG()
    public void setAnalyzer(ITestContext context) {
        for (ITestNGMethod testMethod : context.getAllTestMethods()) {
            testMethod.setRetryAnalyzer(new RetryListenerTestNG());
        }
    }

    @AfterSuite
    public void saveFailed() {
        RetryListenerTestNG.saveFailedTests();
    }

    @Test(groups = {"sum1"})
    public void sumTestNGTest() {
        CalcSteps calcSteps = new CalcSteps();
        Assert.assertTrue(calcSteps.isPositive(-10));
    }

    @Test(groups = {"sum2"})
    public void sumTestNGTest2() {
        CalcSteps calcSteps = new CalcSteps();
        Assert.assertTrue(calcSteps.isPositive(-20));
    }

    @DataProvider(name = "testUsers")
    public Object[] dataWithUsers() {
        People craig = new People("Craig", 25, "male");
        People catrin = new People("Catrin", 20, "female");
        People floyd = new People("Floyd", 30, "male");
        return new Object[]{craig, catrin, floyd};
    }

    @Test(dataProvider = "testUsers")
    public void testUsersWithRole(People people) {
        System.out.println(people.getName());
        Assert.assertTrue(people.getAge() > 18);
        // some logic
        Assert.assertTrue(people.getName().contains("a"));
    }

    @DataProvider(name = "ips") // DataProvider необходим для сбора и агрегации данных
    public Object[] testIpAddresses() {
        List<String> ips = new ArrayList<>();
        ips.add("127.0.0.1");
        ips.add("localhost");
        ips.add("47.32.125.80");

        // return ips.stream().map(x->new Object[]{x}).toArray();
        return ips.toArray();
    }

    @Test(dataProvider = "ips")
    public void ipTest(String ip) {
        System.out.println(ip);
        Assert.assertTrue(ip.matches("^([0-9]+(\\.|$)){4}"));
    }

    @Test(dataProviderClass = DataTestArguments.class, dataProvider = "argsForCalc")
    public void calcTest(int a, int b, int c) {
        Assert.assertEquals(a + b, c);
    }

    @Test(dataProviderClass = DataTestArguments.class, dataProvider = "diffArgs")
    public void someMagicTransform(int a, String b) {
        Assert.assertEquals(convert(a), b);
    }

    private String convert(int a) {
        switch (a) {
            case 1:
                return "one";
            case 2:
                return "two";
            case 5:
                return "five";
            default:
                return null;
        }

    }
}
