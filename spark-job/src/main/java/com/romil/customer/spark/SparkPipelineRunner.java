package com.romil.customer.spark;

import com.romil.customer.spark.reader.DataReaderFactory;
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
import com.romil.customer.spark.transformation.FilterExecutor;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.HashMap;
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
            Map<String, Dataset<Row>> datasets =
                    new HashMap<>();

            for (DatasetConfig datasetConfig : config.getDatasets()) {

                Dataset<Row> dataset =
                        DataReaderFactory
                                .getReader(
                                        datasetConfig.getFormat()
                                )
                                .read(
                                        spark,
                                        datasetConfig.getPath()
                                );

                datasets.put(
                        datasetConfig.getName(),
                        dataset
                );
            }

            Customer360BuilderSpark builder =
                    new Customer360BuilderSpark();

            Dataset<Row> customer360 =
                    builder.build(
                            datasets,
                            config.getJoins()
                    );

            FilterExecutor filterExecutor =
                    new FilterExecutor();

            Dataset<Row> filteredCustomer360 =
                    filterExecutor.execute(
                            customer360,
                            config.getFilters()
                    );

            CustomerAttributeTransformer attributeTransformer =
                    new CustomerAttributeTransformer(
                            config.getAttributeRules()
                    );

            Dataset<Row> enrichedCustomer360 =
                    attributeTransformer.apply(filteredCustomer360);

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

            new SparkReportWriter()
                    .write(
                            output,
                            config.getOutput()
                    );

        } finally {

            spark.stop();
        }
    }
}