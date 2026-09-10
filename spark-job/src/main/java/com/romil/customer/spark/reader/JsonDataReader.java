package com.romil.customer.spark.reader;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class JsonDataReader
        implements DataReader {

    @Override
    public Dataset<Row> read(
            SparkSession spark,
            String path
    ) {

        return spark.read()
                .json(path);
    }
}