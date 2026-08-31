package com.romil.customer.spark;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

public class SparkReportWriter {

    public void write(Dataset<Row> data) {

        data.coalesce(1)
                .write()
                .option("header", "true")
                .mode("overwrite")
                .csv("../reports/customer-risk-spark-output");
    }
}