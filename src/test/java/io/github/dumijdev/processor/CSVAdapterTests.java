package io.github.dumijdev.processor;

import io.github.dumijdev.processor.models.*;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class CSVAdapterTests {

    private static File file;
    private static File file1;

    @BeforeAll
    @SneakyThrows
    static void createTempFile() {
        InputStream in = CSVDataReaderTests.class.getResourceAsStream("/data/test.csv");

        Path path = Files.createTempFile("test", ".csv");

        file = path.toFile();

        FileOutputStream writer = new FileOutputStream(file);

        writer.write(Objects.requireNonNull(in).readAllBytes());

        writer.flush();

        writer.close();
    }

    @BeforeAll
    @SneakyThrows
    static void createTempFile1() {
        InputStream in = CSVDataReaderTests.class.getResourceAsStream("/data/test1.csv");

        Path path = Files.createTempFile("test1", ".csv");

        file1 = path.toFile();

        FileOutputStream writer = new FileOutputStream(file1);

        writer.write(Objects.requireNonNull(in).readAllBytes());

        writer.flush();

        writer.close();
    }

    @SneakyThrows
    @Test
    public void shouldAdaptLineToPersonWithPosition() {

        var adapter = new DefaultAdapter();

        var reader = new CSVDataReader();

        var csv = reader.read(file);

        for (var line : csv) {
            var p = adapter.adapt(line, Person.class);

            Assertions.assertNotNull(p.getName());
        }


    }

    @SneakyThrows
    @Test
    public void shouldAdaptLineToPersonWithName() {

        var adapter = new DefaultAdapter();

        var reader = new CSVDataReader();

        var csv = reader.read(file1, true);

        for (var line : csv) {
            var p = adapter.adapt(line, Person1.class);

            Assertions.assertNotNull(p.getName());
        }
    }

    @SneakyThrows
    @Test
    public void shouldAdaptLineToPersonWithoutNameAndPosition() {

        var adapter = new DefaultAdapter();

        var reader = new CSVDataReader();

        var csv = reader.read(file1, true);

        for (var line : csv) {
            var p = adapter.adapt(line, Person2.class);

            Assertions.assertNotNull(p.getName());
        }
    }

    @SneakyThrows
    @Test
    public void shouldAdaptLineToPersonCSVColumnAnnotation() {

        var adapter = new DefaultAdapter();

        var reader = new CSVDataReader();

        var csv = reader.read(file1, true);

        for (var line : csv) {
            var p = adapter.adapt(line, Person3.class);

            Assertions.assertNotNull(p.getName());
        }
    }

    @SneakyThrows
    @Test
    public void shouldThrowsRuntimeException() {

        var adapter = new DefaultAdapter();

        var reader = new CSVDataReader();

        var csv = reader.read(file1, true);

        for (var line : csv)
            Assertions.assertThrows(RuntimeException.class, () -> adapter.adapt(line, Person4.class));
    }

    @SneakyThrows
    @Test
    public void shouldIgnoreNoMappedField() {

        var adapter = new DefaultAdapter();

        var reader = new CSVDataReader();

        var csv = reader.read(file1, true);

        for (var line : csv) {
            var p = adapter.adapt(line, Person5.class);

            Assertions.assertNotNull(p.getName());
        }
    }
}

