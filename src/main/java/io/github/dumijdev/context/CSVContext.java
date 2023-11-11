package io.github.dumijdev.context;

import io.github.dumijdev.models.CSV;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class CSVContext {
    private static CSVContext context;
    private final Map<String, CSV> data;

    private CSVContext() {
        data = new HashMap<>();
    }

    public static CSVContext getInstance() {
        if (context == null) {
            context = new CSVContext();
        }

        return context;
    }
}
