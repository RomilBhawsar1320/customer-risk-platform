package com.romil.customer.spark.writer;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

public class ParquetDataWriter implements DataWriter {

    @Override
    public void write(
            Dataset<Row> dataset,
            String path
    ) {

        dataset.write()
                .mode("overwrite")
                .parquet(path);
    }
}