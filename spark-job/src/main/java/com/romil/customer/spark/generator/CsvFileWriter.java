package com.romil.customer.spark.generator;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public class CsvFileWriter implements Closeable {

    private final BufferedWriter writer;

    public CsvFileWriter(
            Path filePath,
            String header
    ) throws IOException {

        this.writer =
                new BufferedWriter(
                        new FileWriter(filePath.toFile()),
                        1024 * 1024
                );

        writer.write(header);
        writer.newLine();
    }

    public void writeLine(
            String line
    ) throws IOException {

        writer.write(line);
        writer.newLine();
    }

    @Override
    public void close()
            throws IOException {

        writer.close();
    }
}