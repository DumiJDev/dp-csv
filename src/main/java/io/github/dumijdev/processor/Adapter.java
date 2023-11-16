package io.github.dumijdev.processor;

import io.github.dumijdev.models.Adaptable;
import io.github.dumijdev.models.CSVLine;

public interface Adapter {
    <T> T adapt(CSVLine line, Class<T> type);
    default <T> T adapt(CSVLine line, Adaptable<T> adaptable) {
        return adaptable.toBean(line);
    }
}
