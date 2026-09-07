package com.romil.customer.spark.transformation;

import com.romil.customer.spark.config.FilterConfig;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import java.util.List;

import static org.apache.spark.sql.functions.expr;

public class FilterExecutor {

    public Dataset<Row> execute(
            Dataset<Row> df,
            List<FilterConfig> filters) {

        Dataset<Row> result = df;

        for (FilterConfig filter : filters) {

            result =
                    result.filter(
                            expr(filter.getCondition())
                    );
        }

        return result;
    }
}