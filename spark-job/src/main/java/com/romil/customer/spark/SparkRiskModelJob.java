package com.romil.customer.spark;

import com.romil.customer.spark.config.RiskRuleConfig;

import org.apache.spark.sql.Column;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import java.util.List;
import static org.apache.spark.sql.functions.lit;
import static org.apache.spark.sql.functions.when;
import static org.apache.spark.sql.functions.expr;

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

        for (int i = rules.size() - 1; i >= 0; i--) {

            RiskRuleConfig rule = rules.get(i);

            Column condition =
                    expr(rule.getCondition());

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