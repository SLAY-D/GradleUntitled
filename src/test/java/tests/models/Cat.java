package tests.models;

import lombok.*;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
public class Cat {
    private String name;
    private String breed;
    private String color;
    private Integer age;
}
