package com.romil.customer.spark.transformation;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

public class Customer360BuilderSpark {

    public Dataset<Row> build(
            Dataset<Row> customer,
            Dataset<Row> bureau,
            Dataset<Row> transaction,
            Dataset<Row> product,
            Dataset<Row> marketing) {

        return customer
                .join(bureau, "customer_id")
                .join(transaction, "customer_id")
                .join(product, "customer_id")
                .join(marketing, "customer_id");
    }
}