package io.github.dumijdev.exception;

public class BadDefinitionCSVColumnException extends RuntimeException {
    public BadDefinitionCSVColumnException() {
    }

    public BadDefinitionCSVColumnException(String message) {
        super(message);
    }
}
