package com.romil.customer.spark.transformation;

import com.romil.customer.spark.config.RiskWeightsConfig;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import static org.apache.spark.sql.functions.col;
import static org.apache.spark.sql.functions.lit;

public class RiskScoreTransformer {

    private final RiskWeightsConfig config;

    public RiskScoreTransformer(
            RiskWeightsConfig config) {

        this.config = config;
    }

    public Dataset<Row> apply(
            Dataset<Row> df) {

        return df.withColumn(
                "risk_score",

                col("credit_score")
                        .multiply(
                                lit(
                                        config.getCreditScoreWeight()
                                )
                        )

                        .plus(
                                col("income")
                                        .divide(
                                                config.getIncomeDivisor()
                                        )
                        )

                        .plus(
                                col("avg_balance")
                                        .divide(
                                                config.getBalanceDivisor()
                                        )
                        )
        );
    }
}