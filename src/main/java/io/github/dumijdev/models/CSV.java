package io.github.dumijdev.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@Builder
@AllArgsConstructor
@Getter
public class CSV implements Iterable<CSVLine> {
    private CSVHeader header;
    private final List<CSVLine> lines;
    private final Separator sep;

    public CSV(List<CSVLine> lines) {
        this.lines = lines;
        this.sep = Separator.COMMA;
    }

    public static CSV empty() {
        return new CSV(new LinkedList<>());
    }

    public static CSV noBody(CSVHeader header) {
        var csv = new CSV(new LinkedList<>());
        csv.header = header;
        return csv;
    }

    @Override
    public Iterator<CSVLine> iterator() {
        return lines.iterator();
    }

    public void add(String line) {
        add(new CSVLine(line.split(sep.getSep())));
    }

    public void add(String line, Separator sep) {
        add(new CSVLine(line.split(sep.getSep())));
    }

    public void add(List<CSVColumn> columns) {
        add(new CSVLine(columns));
    }

    public void add(CSVLine line) {
        lines.add(line);
    }
}
