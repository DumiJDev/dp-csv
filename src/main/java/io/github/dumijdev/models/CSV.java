package io.github.dumijdev.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@AllArgsConstructor
@Getter
public class CSV {
    private final String headers;
    private final List<Line> lines;

    public static CSV empty() {
        return new CSV("", List.of());
    }
}
