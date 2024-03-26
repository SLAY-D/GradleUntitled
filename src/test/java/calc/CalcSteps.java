package calc;

import io.qameta.allure.Step;

public class CalcSteps {
    @Step("Сложение двух чисел {a} + {b}")
    public int sum(int a, int b){
        return a+b;
    }

    @Step("Число {result} больше нуля")
    public boolean isPositive(int result){
        return result>0;
    }


}
