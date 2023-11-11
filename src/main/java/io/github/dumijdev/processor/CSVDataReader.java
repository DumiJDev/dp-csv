package io.github.dumijdev.processor;

import io.github.dumijdev.context.CSVContext;
import io.github.dumijdev.models.CSV;
import io.github.dumijdev.models.Separator;
import lombok.AllArgsConstructor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@AllArgsConstructor
public class CSVDataReader {
    private final CSVContext context;

    public CSV read(File file, Separator separator) {
        try (FileInputStream fis = new FileInputStream(file)) {
            String header;
            String[] data = new String(fis.readAllBytes()).split(separator.getSep());


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return CSV.empty();
    }
}
