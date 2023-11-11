package io.github.dumijdev.models;

import lombok.Getter;

@Getter
public enum Separator {
    COMMA(","), SEMICOLON(";");

    private final String sep;

    Separator(String sep) {
        this.sep = sep;
    }
}
