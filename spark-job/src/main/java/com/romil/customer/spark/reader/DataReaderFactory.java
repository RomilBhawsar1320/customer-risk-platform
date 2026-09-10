package com.romil.customer.spark.reader;

public final class DataReaderFactory {

    private DataReaderFactory() {
    }

    public static DataReader getReader(
            String format
    ) {

        return switch (format.toLowerCase()) {

            case "csv" ->
                    new CsvDataReader();

            case "parquet" ->
                    new ParquetDataReader();

            case "json" ->
                    new JsonDataReader();

            default ->
                    throw new IllegalArgumentException(
                            "Unsupported format: "
                                    + format
                    );
        };
    }
}