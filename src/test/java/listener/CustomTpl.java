package listener;

import io.qameta.allure.restassured.AllureRestAssured;

// Надстройка для Allure отчета. Отчеты становятся более информативными

public class CustomTpl {
    private static final AllureRestAssured FILTER = new AllureRestAssured();

    private CustomTpl(){
    }

    public static CustomTpl customLogFilter(){
        return initLogFilter.logFilter;
    }

    public AllureRestAssured withCustomTemplate(){
        FILTER.setRequestTemplate("request.ftl");
        FILTER.setResponseTemplate("response.ftl");
        return FILTER;
    }

    public static class initLogFilter{
        private static final CustomTpl logFilter = new CustomTpl();
    }
}
