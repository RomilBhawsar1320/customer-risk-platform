package com.romil.customer.spark;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class Customer360SparkJob {

    public static void main(String[] args) {

        SparkSession spark = SparkSessionFactory.create();

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

            System.out.println();
            System.out.println("=================================");
            System.out.println("CUSTOMER 360 DATASET");
            System.out.println("=================================");

            customer360.show(false);

            System.out.println();
            System.out.println("=================================");
            System.out.println("CUSTOMER 360 SCHEMA");
            System.out.println("=================================");

            customer360.printSchema();

            System.out.println();
            System.out.println("=================================");
            System.out.println("TOTAL RECORDS : " + customer360.count());
            System.out.println("=================================");

        } finally {

            spark.stop();
        }
    }
}