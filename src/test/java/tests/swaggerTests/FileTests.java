package tests.swaggerTests;

import io.qameta.allure.Attachment;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import listener.CustomTpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import services.FileService;
import services.UserService;
import java.io.File;
import java.util.Random;
import static assertions.Conditions.hasMessage;
import static assertions.Conditions.hasStatusCode;

// Прикрепление/загрузка файлов

public class FileTests {
    private static FileService fileService;

    @BeforeAll
    public static void setUp(){
        RestAssured.baseURI = "http://85.192.34.140:8080/";
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter(), CustomTpl.customLogFilter().withCustomTemplate());
        fileService = new FileService();
    }

    // Прикрепляет файл к Allure отчету
    @Attachment(value = "downloaded", type = "image/png")
    private byte[] attachFile(byte[] bytes){
        return bytes;
    }

    @Test
    public void positiveDownloadTest(){
        byte[] file = fileService.downloadBaseImage()
                .asResponse().asByteArray();
        attachFile(file);
        File expectedFile = new File("src/test/resources/threadqa.jpeg");

        Assertions.assertEquals(expectedFile.length(), file.length);
    }

    @Test
    public void positiveUploadTest(){
        File expectedFile = new File("src/test/resources/capibara.jpg");
        fileService.uploadFile(expectedFile)
                .should(hasStatusCode(200))
                .should(hasMessage("file uploaded to server"));

        byte[] actualFile = fileService.downloadLastFile().asResponse().asByteArray();
        Assertions.assertTrue(actualFile.length != 0);
        Assertions.assertEquals(expectedFile.length(),actualFile.length);


    }
}
