package io.github.dumijdev.processor.models;

import io.github.dumijdev.stereotype.CSVColumn;
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
public class Person2 {

    @CSVColumn
    private String name;

    @CSVColumn
    private int age;
}
