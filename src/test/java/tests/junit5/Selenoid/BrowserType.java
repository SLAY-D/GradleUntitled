package tests.junit5.Selenoid;

import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@ExtendWith(BrowserTypeAnnotationProcessing.class)
@Target({ElementType.TYPE,ElementType.METHOD}) // Аннотация навешивается как на класс, так и на метод
@Retention(RetentionPolicy.RUNTIME) // Аннотация будет выполняться во время запуска кода
public @interface BrowserType {

    Browser browser();
    boolean isRemote() default true;

    enum Browser{
        FIREFOX, CHROME
    }
}
