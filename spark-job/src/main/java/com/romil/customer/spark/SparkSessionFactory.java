package com.romil.customer.spark;

import org.apache.spark.sql.SparkSession;

public final class SparkSessionFactory {

    private SparkSessionFactory() {
    }

    public static SparkSession create() {

        return SparkSession.builder()
                .appName("CustomerRiskSpark")
                .master("local[*]")
                .config("spark.sql.shuffle.partitions", "4")
                .getOrCreate();
    }
}