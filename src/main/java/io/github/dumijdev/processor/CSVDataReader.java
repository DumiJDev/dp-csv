package io.github.dumijdev.processor;

import io.github.dumijdev.models.CSV;
import io.github.dumijdev.models.Separator;
import lombok.AllArgsConstructor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@AllArgsConstructor
public class CSVDataReader {

    public CSV read(File file, Separator separator) throws FileNotFoundException {
        try (FileInputStream fis = new FileInputStream(file)) {
            String[] data = new String(fis.readAllBytes()).split("\n");

            if (data.length == 0) {
                return CSV.empty();
            }

            var csv = CSV.empty();

            for (var line : data) {
                csv.add(line, separator);
            }

            return csv;

        } catch (IOException e) {
            throw new FileNotFoundException(e.getMessage());
        }
    }

    public CSV read(File file) throws FileNotFoundException {
        return read(file, Separator.COMMA);
    }
}
