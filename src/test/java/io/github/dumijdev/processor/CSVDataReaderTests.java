package io.github.dumijdev.processor;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class CSVDataReaderTests {

    private File file;

    @BeforeEach
    @SneakyThrows
    void createTempFile() {
        InputStream in = CSVDataReaderTests.class.getResourceAsStream("/data/test.csv");

        Path path = Files.createTempFile("test", ".csv");

        file = path.toFile();

        FileOutputStream writer = new FileOutputStream(file);

        writer.write(Objects.requireNonNull(in).readAllBytes());

        writer.flush();

        writer.close();
    }

    @SneakyThrows
    @Test
    public void shouldReturnsACSV() {
        var reader = new CSVDataReader();

        var csv = reader.read(file);

        assertNotNull(csv);

    }

    @SneakyThrows
    @Test
    public void shouldReturnsACSVNotEmpty() {
        var reader = new CSVDataReader();

        var csv = reader.read(file);

        for (var line : csv) {
            System.out.println(line);
        }

        assertFalse(csv.getLines().isEmpty());

    }

    @Test
    public void shouldThrowsRuntimeException() {
        var reader = new CSVDataReader();

        assertThrows(FileNotFoundException.class, () -> reader.read(new File("")));
    }
}
