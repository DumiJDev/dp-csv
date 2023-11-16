package io.github.dumijdev.exception;

public class NoCSVEntityException extends RuntimeException {
    public NoCSVEntityException() {
    }

    public NoCSVEntityException(String message) {
        super(message);
    }
}
