package io.github.dumijdev.processor;

import io.github.dumijdev.models.CSV;
import io.github.dumijdev.models.CSVColumn;
import io.github.dumijdev.models.CSVHeader;
import io.github.dumijdev.models.Separator;
import lombok.AllArgsConstructor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

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

    public CSV read(File file, boolean withHeader, Separator separator) throws FileNotFoundException {

        if (!withHeader) {
            return read(file, separator);
        }

        try (FileInputStream fis = new FileInputStream(file)) {
            String[] data = new String(fis.readAllBytes()).split("\n");

            if (data.length == 0) {
                return CSV.empty();
            }

            var csv = CSV.noBody(new CSVHeader(data[0].split(separator.getSep())));

            for (var i = 1; i < data.length; i++) {
                List<CSVColumn> columns = new LinkedList<>();

                var cols = data[i].split(separator.getSep());
                for (var j = 0; j < cols.length; j++) {
                    columns.add(new CSVColumn(csv.getHeader().getColumnByPosition(j).getValue(), j, cols[j].trim()));
                }

                csv.add(columns);
            }

            return csv;

        } catch (IOException e) {
            throw new FileNotFoundException(e.getMessage());
        }
    }

    public CSV read(File file, boolean withHeader) throws FileNotFoundException {
        return read(file, withHeader, Separator.COMMA);
    }
}
