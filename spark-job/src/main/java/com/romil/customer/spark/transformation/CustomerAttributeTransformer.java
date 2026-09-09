package com.romil.customer.spark.transformation;

import com.romil.customer.spark.config.AttributeRulesConfig;
import org.apache.spark.sql.Column;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import java.util.Map;

import static org.apache.spark.sql.functions.expr;
import static org.apache.spark.sql.functions.lit;
import static org.apache.spark.sql.functions.when;

public class CustomerAttributeTransformer {

    private final AttributeRulesConfig config;

    public CustomerAttributeTransformer(
            AttributeRulesConfig config) {

        this.config = config;
    }

    @SuppressWarnings("unchecked")
    public Dataset<Row> apply(Dataset<Row> df) {

        return df
                .withColumn(
                        "income_band",
                        buildExpression(config.getIncomeBand())
                )
                .withColumn(
                        "customer_segment",
                        buildExpression(config.getCustomerSegment())
                )
                .withColumn(
                        "spend_band",
                        buildExpression(config.getSpendBand())
                )
                .withColumn(
                        "credit_band",
                        buildExpression(config.getCreditBand())
                );
    }

    private Column buildExpression(
            Map<String, String> rules) {

        Column column = null;

        for (Map.Entry<String, String> entry : rules.entrySet()) {

            if (column == null) {

                column = when(
                        expr(entry.getValue()),
                        lit(entry.getKey())
                );

            } else {

                column = column.when(
                        expr(entry.getValue()),
                        lit(entry.getKey())
                );
            }
        }

        return column;
    }
}