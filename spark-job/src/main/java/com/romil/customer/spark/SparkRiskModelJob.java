package com.romil.customer.spark;

import com.romil.customer.spark.config.RiskRuleConfig;

import org.apache.spark.sql.Column;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import java.util.List;

import static org.apache.spark.sql.functions.col;
import static org.apache.spark.sql.functions.lit;
import static org.apache.spark.sql.functions.when;

public class SparkRiskModelJob {

    private final List<RiskRuleConfig> rules;

    public SparkRiskModelJob(
            List<RiskRuleConfig> rules) {

        this.rules = rules;
    }

    public Dataset<Row> apply(
            Dataset<Row> customer360) {

        Column riskExpr = lit("UNCLASSIFIED");

        Column offerExpr = lit("UNKNOWN");

        for (RiskRuleConfig rule : rules) {

            Column condition = lit(true);

            if (rule.getMinCreditScore() != null) {

                condition = condition.and(
                        col("credit_score")
                                .geq(rule.getMinCreditScore())
                );
            }

            if (rule.getMaxCreditScore() != null) {

                condition = condition.and(
                        col("credit_score")
                                .leq(rule.getMaxCreditScore())
                );
            }

            if (rule.getMinIncome() != null) {

                condition = condition.and(
                        col("income")
                                .geq(rule.getMinIncome())
                );
            }

            riskExpr =
                    when(
                            condition,
                            rule.getRiskCategory()
                    ).otherwise(riskExpr);

            offerExpr =
                    when(
                            condition,
                            rule.getOffer()
                    ).otherwise(offerExpr);
        }

        return customer360
                .withColumn(
                        "risk_category",
                        riskExpr
                )
                .withColumn(
                        "offer",
                        offerExpr
                );
    }
}