package com.romil.customer.spark;

import com.romil.customer.spark.config.OutputConfig;
import com.romil.customer.spark.writer.DataWriterFactory;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

public class SparkReportWriter {

    public void write(
            Dataset<Row> data,
            OutputConfig outputConfig
    ) {

        System.out.println(
                "STARTING WRITE : "
                        + outputConfig.getFormat()
        );

        DataWriterFactory
                .getWriter(
                        outputConfig.getFormat()
                )
                .write(
                        data,
                        outputConfig.getPath()
                );

        System.out.println(
                "WRITE COMPLETED"
        );
    }
}