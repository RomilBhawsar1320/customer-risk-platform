package com.romil.customer.spark;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

public class SparkReportWriter {

    public void write(Dataset<Row> data) {

        System.out.println("STARTING WRITE");

        data.coalesce(1)
                .write()
                .option("header", "true")
                .mode("overwrite")
                .csv("target/customer-risk-spark-output");

        System.out.println("WRITE COMPLETED");
    }
}