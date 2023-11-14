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
@CSVPath(hasHeader = false)
@NoArgsConstructor
public class Person {

    @CSVColumn(position = 0)
    private String name;

    @CSVColumn(position = 1)
    private int age;
}