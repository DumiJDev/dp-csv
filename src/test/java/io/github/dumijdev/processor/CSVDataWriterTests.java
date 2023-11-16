package io.github.dumijdev.processor;

import io.github.dumijdev.models.CSV;
import io.github.dumijdev.models.CSVLine;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
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

    @DisplayName("should write file from CSV model")
    @SneakyThrows
    @Test
    public void shouldWriteFileFromCSVModel() {
        CSVDataWriter writer = new CSVDataWriter();
        CSV csv = new CSV(List.of(new CSVLine("dumij", "dev")));

        writer.write(file, csv);

        CSVDataReader reader = new CSVDataReader();

        var csv1 = reader.read(file);

        Assertions.assertEquals(csv1.getLines().size(), csv.getLines().size());
    }
}
