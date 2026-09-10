package com.romil.customer.spark.writer;

public final class DataWriterFactory {

    private DataWriterFactory() {
    }

    public static DataWriter getWriter(
            String format
    ) {

        return switch (format.toLowerCase()) {

            case "csv" ->
                    new CsvDataWriter();

            case "parquet" ->
                    new ParquetDataWriter();

            case "json" ->
                    new JsonDataWriter();

            default ->
                    throw new IllegalArgumentException(
                            "Unsupported output format: "
                                    + format
                    );
        };
    }
}