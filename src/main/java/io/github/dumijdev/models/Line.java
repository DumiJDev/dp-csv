package io.github.dumijdev.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
@Setter
public class Line {
    private final List<String> columns;
    private Separator separator;

    public Line(String... line) {
        columns = new LinkedList<>();

        columns.addAll(Arrays.asList(line));
    }

    public Line() {
        this.columns = new LinkedList<>();
    }

    @Override
    public String toString() {
        return columns.stream().collect(Collectors.joining(separator.getSep()));
    }
}
