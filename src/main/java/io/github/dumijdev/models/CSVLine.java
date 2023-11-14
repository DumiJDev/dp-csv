package io.github.dumijdev.models;

import io.github.dumijdev.exception.NoMappingFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class CSVLine {
    private final List<CSVColumn> columns;
    private Separator separator;

    public CSVColumn getColumnByName(String name) {
        return columns.stream().filter(c -> c.getName().equals(name)).findFirst().orElseThrow(NoMappingFoundException::new);
    }

    public CSVColumn getColumnByPosition(int position) {
        return columns.stream().filter(c -> c.getPosition() == position).findFirst().orElseThrow(NoMappingFoundException::new);
    }

    public CSVLine(String... line) {
        columns = new LinkedList<>();
        separator = Separator.COMMA;

        for (int i = 0; i < line.length; i++) {
            columns.add(new CSVColumn("", i, line[i].trim()));
        }
    }

    public CSVLine(List<CSVColumn> columns1) {
        columns = new LinkedList<>();
        separator = Separator.COMMA;

        columns.addAll(columns1);
    }

    public CSVLine() {
        this.columns = new LinkedList<>();
        this.separator = Separator.COMMA;
    }

    @Override
    public String toString() {
        return new ArrayList<>(columns).toString();
    }
}
