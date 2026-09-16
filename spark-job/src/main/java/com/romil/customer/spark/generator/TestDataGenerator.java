package com.romil.customer.spark.generator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TestDataGenerator {

    private static final long DEFAULT_ROWS = 1000;

    public static void main(String[] args)
            throws Exception {

        long rows =
                args.length > 0
                        ? Long.parseLong(args[0])
                        : DEFAULT_ROWS;

        generate(rows);
    }

    public static void generate(
            long rows
    ) throws IOException {

        Path outputDir =
                Path.of("sample-data-generated");

        Files.createDirectories(outputDir);

        try (

                CsvFileWriter customerWriter =
                        new CsvFileWriter(
                                outputDir.resolve("customer_master.csv"),
                                "customer_id,customer_name,age,income"
                        );

                CsvFileWriter bureauWriter =
                        new CsvFileWriter(
                                outputDir.resolve("credit_bureau.csv"),
                                "customer_id,credit_score,bureau_status"
                        );

                CsvFileWriter transactionWriter =
                        new CsvFileWriter(
                                outputDir.resolve("transaction_summary.csv"),
                                "customer_id,monthly_spend,avg_balance"
                        );

                CsvFileWriter productWriter =
                        new CsvFileWriter(
                                outputDir.resolve("product_holdings.csv"),
                                "customer_id,product_name"
                        );

                CsvFileWriter marketingWriter =
                        new CsvFileWriter(
                                outputDir.resolve("marketing_preferences.csv"),
                                "customer_id,email_opt_in"
                        )

        ) {

            for (long customerId = 100001;
                 customerId < 100001 + rows;
                 customerId++) {

                CustomerData customer =
                        RandomDataFactory.generate(
                                customerId
                        );

                customerWriter.writeLine(
                        customer.customerId() + ","
                                + customer.customerName() + ","
                                + customer.age() + ","
                                + customer.income()
                );

                bureauWriter.writeLine(
                        customer.customerId() + ","
                                + customer.creditScore() + ","
                                + customer.bureauStatus()
                );

                transactionWriter.writeLine(
                        customer.customerId() + ","
                                + customer.monthlySpend() + ","
                                + customer.avgBalance()
                );

                productWriter.writeLine(
                        customer.customerId() + ","
                                + customer.productName()
                );

                marketingWriter.writeLine(
                        customer.customerId() + ","
                                + customer.emailOptIn()
                );

                if (customerId % 100000 == 0) {

                    System.out.println(
                            "Generated "
                                    + (customerId - 100000)
                                    + " records"
                    );
                }
            }
        }

        System.out.println(
                "Generation completed successfully"
        );
    }
}