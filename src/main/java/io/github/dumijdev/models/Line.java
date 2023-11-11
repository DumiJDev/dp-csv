package io.github.dumijdev.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class Line {
    private List<String> columns;
}
