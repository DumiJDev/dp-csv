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
public class CSV implements Iterable<Line> {
    private final List<Line> lines;
    private final Separator sep;

    public CSV(List<Line> lines) {
        this.lines = lines;
        this.sep = Separator.COMMA;
    }

    public static CSV empty() {
        return new CSV(new LinkedList<>());
    }

    @Override
    public Iterator<Line> iterator() {
        return lines.iterator();
    }

    public void add(String line) {
        add(new Line(line.split(sep.getSep())));
    }

    public void add(String line, Separator sep) {
        add(new Line(line.split(sep.getSep())));
    }

    public void add(Line line) {
        lines.add(line);
    }
}
