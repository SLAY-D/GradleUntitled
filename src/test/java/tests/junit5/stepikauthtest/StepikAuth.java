package tests.junit5.stepikauthtest;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class StepikAuth {
    private String email;
    private String password;
}
