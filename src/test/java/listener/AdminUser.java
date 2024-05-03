package listener;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
    Необходимо для того, чтобы заранее собрать конфиг администратора и передавать его в дальнейшем в качестве аннотации
 */
@Target(ElementType.PARAMETER) // Подстановка аннотации в аргумент
@Retention(RetentionPolicy.RUNTIME) // Считывание аннотации в реальном времени
public @interface AdminUser {
}
