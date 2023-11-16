package io.github.dumijdev.models;

import java.util.Arrays;

public class CSVHeader extends CSVLine {
    public CSVHeader(String... line) {
        super(Arrays.stream(line).map(s -> s.replace("\n", "")).map(String::toLowerCase).toArray(String[]::new));
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
