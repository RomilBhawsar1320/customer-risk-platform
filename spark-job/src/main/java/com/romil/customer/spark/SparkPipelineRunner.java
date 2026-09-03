package com.romil.customer.spark;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import com.romil.customer.spark.transformation.Customer360BuilderSpark;
import static org.apache.spark.sql.functions.col;
import com.romil.customer.spark.transformation.CustomerAttributeTransformer;
import com.romil.customer.spark.transformation.CustomerRankingTransformer;
import com.romil.customer.spark.transformation.CustomerMetricsAggregator;

public class SparkPipelineRunner {

    public static void main(String[] args) {

        SparkSession spark =
                SparkSessionFactory.create();

        try {

            Dataset<Row> customer =
                    spark.read()
                            .option("header", "true")
                            .option("inferSchema", "true")
                            .csv("../sample-data/customer_master.csv");

            Dataset<Row> bureau =
                    spark.read()
                            .option("header", "true")
                            .option("inferSchema", "true")
                            .csv("../sample-data/credit_bureau.csv");

            Dataset<Row> transaction =
                    spark.read()
                            .option("header", "true")
                            .option("inferSchema", "true")
                            .csv("../sample-data/transaction_summary.csv");

            Dataset<Row> product =
                    spark.read()
                            .option("header", "true")
                            .option("inferSchema", "true")
                            .csv("../sample-data/product_holdings.csv");

            Dataset<Row> marketing =
                    spark.read()
                            .option("header", "true")
                            .option("inferSchema", "true")
                            .csv("../sample-data/marketing_preferences.csv");

            Customer360BuilderSpark builder =
                    new Customer360BuilderSpark();

            Dataset<Row> customer360 =
                    builder.build(
                            customer,
                            bureau,
                            transaction,
                            product,
                            marketing
                    );

            CustomerAttributeTransformer attributeTransformer =
                    new CustomerAttributeTransformer();

            Dataset<Row> enrichedCustomer360 =
                    attributeTransformer.apply(customer360);

            Dataset<Row> customer360WithScore =
                    enrichedCustomer360.withColumn(
                            "risk_score",
                            col("credit_score")
                                    .plus(col("income").divide(1000))
                                    .plus(col("avg_balance").divide(5000))
                    );

            customer360WithScore.select(
                    "customer_id",
                    "income_band",
                    "customer_segment",
                    "spend_band",
                    "credit_band",
                    "risk_score"
            ).show(false);

            customer360WithScore.show(false);

            CustomerRankingTransformer rankingTransformer =
                    new CustomerRankingTransformer();

            Dataset<Row> rankedCustomer360 =
                    rankingTransformer.apply(customer360WithScore);

            rankedCustomer360.select(
                    "customer_id",
                    "credit_score",
                    "credit_score_rank",
                    "credit_score_percentile"
            ).show(false);

            SparkRiskModelJob model =
                    new SparkRiskModelJob();

            Dataset<Row> output =
                    model.apply(rankedCustomer360);

            output.show(false);

            CustomerMetricsAggregator aggregator =
                    new CustomerMetricsAggregator();

            Dataset<Row> metrics =
                    aggregator.aggregate(output);

            metrics.show(false);

//            new SparkReportWriter()
//                    .write(output);

        } finally {

            spark.stop();
        }
    }
}