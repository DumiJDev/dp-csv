package io.github.dumijdev.models;

public interface Adaptable<T> {

    T toBean(CSVLine CSVLine);
    CSVLine toLine();
}
