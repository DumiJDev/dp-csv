package io.github.dumijdev.processor;

import io.github.dumijdev.models.CSV;
import io.github.dumijdev.models.Separator;

import java.io.*;

public class CSVDataWriter {

    public void write(File file, CSV data, Separator separator) throws FileNotFoundException {
        try (FileOutputStream fos = new FileOutputStream(file)) {

            var builder = new StringBuilder();
            for (var line : data) {
                line.setSeparator(separator);
                builder.append(line).append('\n');
            }

            fos.write(builder.toString().getBytes());
            fos.flush();

        } catch (IOException e) {
            throw new FileNotFoundException(e.getMessage());
        }
    }

    public void write(File file, CSV data) throws FileNotFoundException {
        write(file, data, Separator.COMMA);
    }
}
