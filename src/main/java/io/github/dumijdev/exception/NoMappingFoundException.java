package io.github.dumijdev.exception;

public class NoMappingFoundException extends RuntimeException {
    public NoMappingFoundException() {
    }

    public NoMappingFoundException(String message) {
        super(message);
    }
}
