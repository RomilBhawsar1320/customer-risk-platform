package com.romil.customer.spark.transformation;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import static org.apache.spark.sql.functions.avg;
import static org.apache.spark.sql.functions.count;

public class CustomerMetricsAggregator {

    public Dataset<Row> aggregate(Dataset<Row> df) {

        return df.groupBy("bureau_status")
                .agg(
                        count("*").alias("customer_count"),
                        avg("credit_score").alias("avg_credit_score"),
                        avg("income").alias("avg_income"),
                        avg("monthly_spend").alias("avg_monthly_spend")
                );
    }
}