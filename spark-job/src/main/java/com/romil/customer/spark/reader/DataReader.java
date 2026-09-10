package com.romil.customer.spark.reader;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public interface DataReader {

    Dataset<Row> read(
            SparkSession spark,
            String path
    );
}