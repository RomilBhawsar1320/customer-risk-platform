package com.romil.customer.spark.transformation;

import com.romil.customer.spark.config.JoinConfig;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import java.util.List;
import java.util.Map;

public class JoinExecutor {

    public Dataset<Row> execute(
            Map<String, Dataset<Row>> datasets,
            List<JoinConfig> joins) {

        Dataset<Row> result = null;

        for (JoinConfig join : joins) {

            Dataset<Row> left =
                    result == null
                            ? datasets.get(join.getLeft())
                            : result;

            Dataset<Row> right =
                    datasets.get(join.getRight());

            result =
                    left.join(
                            right,
                            join.getColumn(),
                            join.getType()
                    );
        }

        return result;
    }
}