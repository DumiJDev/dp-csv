package io.github.dumijdev.processor.models;


import io.github.dumijdev.stereotype.CSVColumnIgnore;
import io.github.dumijdev.stereotype.CSVEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@CSVEntity
public class Person5 {
    private String name;
    private int age;
    @CSVColumnIgnore
    private LocalDateTime time;
}
