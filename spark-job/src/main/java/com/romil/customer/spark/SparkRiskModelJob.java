package com.romil.customer.spark;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import static org.apache.spark.sql.functions.col;
import static org.apache.spark.sql.functions.when;

public class SparkRiskModelJob {

    public Dataset<Row> apply(Dataset<Row> customer360) {

        return customer360

                .withColumn(
                        "risk_category",

                        when(
                                col("credit_score").geq(750)
                                        .and(col("income").geq(100000)),
                                "LOW_RISK"
                        )

                                .when(
                                        col("credit_score").between(650, 749),
                                        "MEDIUM_RISK"
                                )

                                .otherwise("HIGH_RISK")
                );
    }
}