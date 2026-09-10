package com.romil.customer.spark.reader;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class CsvDataReader implements DataReader {

    @Override
    public Dataset<Row> read(
            SparkSession spark,
            String path
    ) {

        return spark.read()
                .option("header", "true")
                .option("inferSchema", "true")
                .csv(path);
    }
}