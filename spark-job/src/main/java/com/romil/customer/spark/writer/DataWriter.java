package com.romil.customer.spark.writer;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

public interface DataWriter {

    void write(
            Dataset<Row> dataset,
            String path
    );
}