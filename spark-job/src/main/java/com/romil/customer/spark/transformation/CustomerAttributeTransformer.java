package com.romil.customer.spark.transformation;

import com.romil.customer.spark.config.AttributeRulesConfig;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import static org.apache.spark.sql.functions.col;
import static org.apache.spark.sql.functions.when;

public class CustomerAttributeTransformer {

    private final AttributeRulesConfig config;

    public CustomerAttributeTransformer(
            AttributeRulesConfig config) {

        this.config = config;
    }

    public Dataset<Row> apply(Dataset<Row> df) {

        return df

                .withColumn(
                        "income_band",
                        when(
                                col("income")
                                        .gt(config.getIncomeBand().getHigh()),
                                "HIGH"
                        )
                                .when(
                                        col("income")
                                                .geq(config.getIncomeBand().getMedium()),
                                        "MEDIUM"
                                )
                                .otherwise("LOW")
                )

                .withColumn(
                        "customer_segment",
                        when(
                                col("income")
                                        .geq(config.getCustomerSegment().getPremium()),
                                "PREMIUM"
                        )
                                .when(
                                        col("income")
                                                .geq(config.getCustomerSegment().getStandard()),
                                        "STANDARD"
                                )
                                .otherwise("BASIC")
                )

                .withColumn(
                        "spend_band",
                        when(
                                col("monthly_spend")
                                        .gt(config.getSpendBand().getHigh()),
                                "HIGH_SPENDER"
                        )
                                .when(
                                        col("monthly_spend")
                                                .geq(config.getSpendBand().getMedium()),
                                        "MEDIUM_SPENDER"
                                )
                                .otherwise("LOW_SPENDER")
                )

                .withColumn(
                        "credit_band",
                        when(
                                col("credit_score")
                                        .gt(config.getCreditBand().getExcellent()),
                                "EXCELLENT"
                        )
                                .when(
                                        col("credit_score")
                                                .geq(config.getCreditBand().getGood()),
                                        "GOOD"
                                )
                                .otherwise("POOR")
                );
    }
}