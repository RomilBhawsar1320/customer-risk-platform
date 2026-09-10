package com.romil.customer.spark.writer;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

public class CsvDataWriter implements DataWriter {

    @Override
    public void write(
            Dataset<Row> dataset,
            String path
    ) {

        dataset.coalesce(1)
                .write()
                .option("header", "true")
                .mode("overwrite")
                .csv(path);
    }
}