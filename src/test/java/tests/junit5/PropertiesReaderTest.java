package tests.junit5;

import lombok.SneakyThrows;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import models.Settings;
import utils.AppConfig;
import utils.JsonHelper;

import java.io.FileInputStream;
import java.util.Properties;

@Tag("UNIT")
public class PropertiesReaderTest {

    // Способы чтения настроек проекта

    // Чтение напрямую из файла
    @Test
    @SneakyThrows
    @Tag("SMOKE")
    public void simpleReaderTest(){
        Properties properties = new Properties();
        FileInputStream fs = new FileInputStream("src/test/resources/project.properties");
        properties.load(fs);
        String url = properties.getProperty("url");
        Boolean isProduction = Boolean.parseBoolean(properties.getProperty("is_production"));
        int threads = Integer.parseInt(properties.getProperty("threads"));
        System.out.println(url);
        System.out.println(isProduction);
        System.out.println(threads);
    }

    // Чтение посредством ранее написанных методов в классе JsonHelper
    @Test
    @SneakyThrows
    public void jacksonReaderTest(){
        Properties properties = new Properties();
        FileInputStream fs = new FileInputStream("src/test/resources/project.properties");
        properties.load(fs);

        String json = JsonHelper.toJson(properties);
        System.out.println(json);

        Settings settings = JsonHelper.fromJsonString(json, Settings.class);
        System.out.println(settings.getUrl());
        System.out.println(settings.getIsProduction());
        System.out.println(settings.getThreads());
    }


    // Чтение через интерфейс AppConfig с использованием библиотеки Owner
    @Test
    public void ownerReaderTest(){
        AppConfig appConfig = ConfigFactory.create(AppConfig.class);
        System.out.println(appConfig.url());
        System.out.println(appConfig.isProd());
        System.out.println(appConfig.threads());
    }
}
