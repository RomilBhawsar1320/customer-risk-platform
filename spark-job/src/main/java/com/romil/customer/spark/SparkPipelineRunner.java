package com.romil.customer.spark;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import com.romil.customer.spark.transformation.Customer360BuilderSpark;
import static org.apache.spark.sql.functions.col;
import com.romil.customer.spark.transformation.CustomerAttributeTransformer;
import com.romil.customer.spark.transformation.CustomerRankingTransformer;
import com.romil.customer.spark.transformation.CustomerMetricsAggregator;
import com.romil.customer.spark.config.DatasetConfig;
import com.romil.customer.spark.config.PipelineConfig;
import com.romil.customer.spark.config.PipelineConfigLoader;
import com.romil.customer.spark.transformation.RiskScoreTransformer;

import java.util.Map;
import java.util.stream.Collectors;

public class SparkPipelineRunner {

    public static void main(String[] args) {

        SparkSession spark =
                SparkSessionFactory.create();

        try {

            PipelineConfig config =
                    PipelineConfigLoader.load(
                            "pipeline-config.json"
                    );

            System.out.println("CONFIG LOADED");

            config.getDatasets()
                    .forEach(dataset ->
                            System.out.println(
                                    dataset.getName() + " -> "
                                            + dataset.getPath()
                            )
                    );

            Map<String, String> datasetPaths =
                    config.getDatasets()
                            .stream()
                            .collect(
                                    Collectors.toMap(
                                            DatasetConfig::getName,
                                            DatasetConfig::getPath
                                    )
                            );
            Dataset<Row> customer =
                    spark.read()
                            .option("header", "true")
                            .option("inferSchema", "true")
                            .csv(datasetPaths.get("customer"));

            Dataset<Row> bureau =
                    spark.read()
                            .option("header", "true")
                            .option("inferSchema", "true")
                            .csv(datasetPaths.get("bureau"));

            Dataset<Row> transaction =
                    spark.read()
                            .option("header", "true")
                            .option("inferSchema", "true")
                            .csv(datasetPaths.get("transaction"));

            Dataset<Row> product =
                    spark.read()
                            .option("header", "true")
                            .option("inferSchema", "true")
                            .csv(datasetPaths.get("product"));

            Dataset<Row> marketing =
                    spark.read()
                            .option("header", "true")
                            .option("inferSchema", "true")
                            .csv(datasetPaths.get("marketing"));

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
                    new CustomerAttributeTransformer(
                            config.getAttributeRules()
                    );

            Dataset<Row> enrichedCustomer360 =
                    attributeTransformer.apply(customer360);

            RiskScoreTransformer riskScoreTransformer =
                    new RiskScoreTransformer(
                            config.getRiskWeights()
                    );

            Dataset<Row> customer360WithScore =
                    riskScoreTransformer.apply(
                            enrichedCustomer360
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
                    new SparkRiskModelJob(
                            config.getRiskRules()
                    );

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