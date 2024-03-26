package tests;

import calc.CalcSteps;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Calc")
public class CalcTest {

    @Test
    public void sumTest(){
        CalcSteps calcSteps = new CalcSteps();
        int result = calcSteps.sum(2,5);
        boolean isOk = calcSteps.isPositive(result);
        Assertions.assertTrue(isOk);
    }
}
