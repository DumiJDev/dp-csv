package io.github.dumijdev.processor;

import io.github.dumijdev.stereotype.CSVColumn;
import io.github.dumijdev.stereotype.CSVEntity;
import io.github.dumijdev.stereotype.CSVPath;
import lombok.Getter;
import lombok.Setter;
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

    @Setter
    @Getter
    @CSVEntity
    @CSVPath(hasHeader = false)
    private static class Person {

        @CSVColumn(position = 0)
        private String name;

        @CSVColumn(position = 1)
        private int age;

        public Person() {
        }
    }

    @Setter
    @Getter
    @CSVEntity
    @CSVPath
    private static class Person1 {

        @CSVColumn(name = "name")
        private String name;

        @CSVColumn(name = "age")
        private int age;

        public Person1() {
        }
    }

    @Setter
    @Getter
    @CSVEntity
    @CSVPath
    private static class Person2 {

        @CSVColumn
        private String name;

        @CSVColumn
        private int age;

        public Person2() {
        }
    }

    @Setter
    @Getter
    @CSVEntity
    @CSVPath
    private static class Person3 {

        private String name;
        private int age;

        public Person3() {
        }
    }

    @Setter
    @Getter
    private static class Person4 {

        private String name;
        private int age;

        public Person4() {
        }
    }

    @SneakyThrows
    @Test
    public void shouldAdaptLineToPersonWithPosition() {

        var adapter = new DefaultAdapter();

        var reader = new CSVDataReader();

        var csv = reader.read(file);

        for (var line : csv) {
            var p = adapter.adapt(line, Person.class);

            Assertions.assertNotNull(p.name);
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

            Assertions.assertNotNull(p.name);
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

            Assertions.assertNotNull(p.name);
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

            Assertions.assertNotNull(p.name);
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
}

