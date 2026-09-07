package com.romil.customer.spark.transformation;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import java.util.List;
import java.util.Map;

import com.romil.customer.spark.config.JoinConfig;

public class Customer360BuilderSpark {

    public Dataset<Row> build(
            Map<String, Dataset<Row>> datasets,
            List<JoinConfig> joins) {

        JoinExecutor executor = new JoinExecutor();

        return executor.execute(
                datasets,
                joins
        );
    }
}