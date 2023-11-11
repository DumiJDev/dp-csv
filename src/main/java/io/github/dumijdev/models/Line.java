package io.github.dumijdev.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@AllArgsConstructor
@Getter
public class Line {
    private List<String> columns;

    public Line(String... line) {
        columns = new LinkedList<>();

        columns.addAll(Arrays.asList(line));
    }

    public Line() {
        this.columns = new LinkedList<>();
    }

    @Override
    public String toString() {
        return Arrays.toString(columns.toArray());
    }
}
