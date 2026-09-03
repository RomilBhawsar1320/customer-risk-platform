package com.romil.customer.spark.transformation;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import static org.apache.spark.sql.functions.col;
import static org.apache.spark.sql.functions.when;

public class CustomerAttributeTransformer {

    public Dataset<Row> apply(Dataset<Row> df) {

        return df

                .withColumn(
                        "income_band",
                        when(col("income").gt(100000), "HIGH")
                                .when(col("income").geq(50000), "MEDIUM")
                                .otherwise("LOW")
                )

                .withColumn(
                        "customer_segment",
                        when(col("income").geq(150000), "PREMIUM")
                                .when(col("income").geq(50000), "STANDARD")
                                .otherwise("BASIC")
                )

                .withColumn(
                        "spend_band",
                        when(col("monthly_spend").gt(50000), "HIGH_SPENDER")
                                .when(col("monthly_spend").geq(20000), "MEDIUM_SPENDER")
                                .otherwise("LOW_SPENDER")
                )

                .withColumn(
                        "credit_band",
                        when(col("credit_score").gt(750), "EXCELLENT")
                                .when(col("credit_score").geq(650), "GOOD")
                                .otherwise("POOR")
                );
    }
}