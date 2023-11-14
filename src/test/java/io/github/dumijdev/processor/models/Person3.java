package io.github.dumijdev.processor.models;

import io.github.dumijdev.stereotype.CSVColumnIgnore;
import io.github.dumijdev.stereotype.CSVEntity;
import io.github.dumijdev.stereotype.CSVPath;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@CSVEntity
@CSVPath
@NoArgsConstructor
public class Person3 {

    private String name;
    private int age;
    @CSVColumnIgnore
    private String ad;
}