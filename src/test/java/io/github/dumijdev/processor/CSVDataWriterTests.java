package io.github.dumijdev.processor;

import io.github.dumijdev.models.CSV;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class CSVDataWriterTests {
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

    @Test
    public void shouldThrowsFileNotFoundException() {
        var writer = new CSVDataWriter();

        Assertions.assertThrows(FileNotFoundException.class, () -> writer.write(new File(""), CSV.empty()));
    }
}
