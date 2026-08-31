package com.romil.customer.spark;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

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

            Dataset<Row> customer360 =
                    customer
                            .join(bureau, "customer_id")
                            .join(transaction, "customer_id")
                            .join(product, "customer_id")
                            .join(marketing, "customer_id");

            SparkRiskModelJob model =
                    new SparkRiskModelJob();

            Dataset<Row> output =
                    model.apply(customer360);

            output.show(false);

            new SparkReportWriter()
                    .write(output);

        } finally {

            spark.stop();
        }
    }
}