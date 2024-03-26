package calc;

import io.qameta.allure.Step;

public class CalcSteps {

    @Step("Складываем числа {a} + {b}")
    public int sum (int a, int b){
        return a+b;
    }

    @Step("Число {result} больше 0")
    public boolean isPositive(int result){
        return result>0;
    }

}
