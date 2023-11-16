package io.github.dumijdev.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class CSVColumn {
    private String name;
    private int position;
    private String value;

    @Override
    public String toString() {
        return "{" +
                "name='" + name + '\'' +
                ", position=" + position +
                ", value='" + value + '\'' +
                '}';
    }
}
