package com.romil.customer.spark.transformation;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.expressions.Window;
import org.apache.spark.sql.expressions.WindowSpec;

import static org.apache.spark.sql.functions.dense_rank;
import static org.apache.spark.sql.functions.percent_rank;
import static org.apache.spark.sql.functions.col;

public class CustomerRankingTransformer {

    public Dataset<Row> apply(Dataset<Row> df) {

        WindowSpec scoreWindow =
                Window.orderBy(
                        col("credit_score").desc()
                );

        return df
                .withColumn(
                        "credit_score_rank",
                        dense_rank().over(scoreWindow)
                )
                .withColumn(
                        "credit_score_percentile",
                        percent_rank().over(scoreWindow)
                );
    }
}