package com.romil.customer.spark.generator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;

public class TestDataGenerator {

    private static final long DEFAULT_ROWS = 1000;
    private static final long STARTING_CUSTOMER_ID = 100001;

    public static void main(String[] args)
            throws Exception {

        long rows =
                args.length > 0
                        ? Long.parseLong(args[0])
                        : DEFAULT_ROWS;

        generate(rows);
    }

    public static void generate(long rows)
            throws IOException {

        if (rows <= 0) {
            throw new IllegalArgumentException(
                    "Row count must be greater than zero"
            );
        }

        long startTime =
                System.currentTimeMillis();

        Path outputDir =
                Path.of("sample-data-generated");

        Files.createDirectories(outputDir);

        try (
                CsvFileWriter customerWriter =
                        new CsvFileWriter(
                                outputDir.resolve(
                                        "customer_master.csv"
                                ),
                                String.join(
                                        ",",
                                        "customer_id",
                                        "customer_name",
                                        "age",
                                        "income",
                                        "occupation",
                                        "employment_type",
                                        "employment_tenure_months",
                                        "city",
                                        "state",
                                        "marital_status",
                                        "dependents",
                                        "education_level",
                                        "account_age_months"
                                )
                        );

                CsvFileWriter bureauWriter =
                        new CsvFileWriter(
                                outputDir.resolve(
                                        "credit_bureau.csv"
                                ),
                        String.join(
                                ",",
                                "customer_id",
                                "credit_score",
                                "bureau_status",
                                "credit_utilization_pct",
                                "missed_payments_12m",
                                "outstanding_loan_amount"
                        )
                        );

                CsvFileWriter transactionWriter =
                        new CsvFileWriter(
                                outputDir.resolve(
                                "transaction_summary.csv"
                                ),
                        String.join(
                                ",",
                                "customer_id",
                                "monthly_spend",
                                "avg_balance",
                                "emi_outflow"
                        )
                        );

                CsvFileWriter productWriter =
                        new CsvFileWriter(
                        outputDir.resolve(
                                "product_holdings.csv"
                        ),
                        "customer_id,product_name"
                        );

                CsvFileWriter marketingWriter =
                        new CsvFileWriter(
                        outputDir.resolve(
                                "marketing_preferences.csv"
                        ),
                        String.join(
                                ",",
                                "customer_id",
                                "email_opt_in",
                                "mobile_app_active_flag"
                        )
                        )
        ) {

            for (long recordNumber = 0;
                 recordNumber < rows;
                 recordNumber++) {

                long customerId =
                        STARTING_CUSTOMER_ID
                                + recordNumber;

                CustomerData customer =
                        RandomDataFactory.generate(
                                customerId
                        );

                customerWriter.writeLine(
                        String.join(
                                ",",
                                String.valueOf(
                                        customer.customerId()
                                ),
                                customer.customerName(),
                                String.valueOf(
                                        customer.age()
                                ),
                                String.valueOf(
                                        customer.income()
                                ),
                                customer.occupation(),
                                customer.employmentType(),
                                String.valueOf(
                                        customer.employmentTenureMonths()
                                ),
                                customer.city(),
                                customer.state(),
                                customer.maritalStatus(),
                                String.valueOf(
                                        customer.dependents()
                                ),
                                customer.educationLevel(),
                                String.valueOf(
                                        customer.accountAgeMonths()
                                )
                        )
                );

                bureauWriter.writeLine(
                        String.join(
                                ",",
                                String.valueOf(
                                        customer.customerId()
                                ),
                                String.valueOf(
                                        customer.creditScore()
                                ),
                                customer.bureauStatus(),
                                String.format(
                                        Locale.US,
                                        "%.2f",
                                        customer.creditUtilizationPct()
                                ),
                                String.valueOf(
                                        customer.missedPayments12m()
                                ),
                                String.format(
                                        Locale.US,
                                        "%.2f",
                                        customer.outstandingLoanAmount()
                                )
                        )
                );

                transactionWriter.writeLine(
                        String.join(
                                ",",
                                String.valueOf(
                                        customer.customerId()
                                ),
                                String.valueOf(
                                        customer.monthlySpend()
                                ),
                                String.valueOf(
                                        customer.avgBalance()
                                ),
                                String.format(
                                        Locale.US,
                                        "%.2f",
                                        customer.emiOutflow()
                                )
                        )
                );

                productWriter.writeLine(
                        String.join(
                                ",",
                                String.valueOf(
                                        customer.customerId()
                                ),
                                customer.productName()
                        )
                );

                marketingWriter.writeLine(
                        String.join(
                                ",",
                                String.valueOf(
                                        customer.customerId()
                                ),
                                String.valueOf(
                                        customer.emailOptIn()
                                ),
                                String.valueOf(
                                        customer.mobileAppActiveFlag()
                                )
                        )
                );

                long generatedCount =
                        recordNumber + 1;

                if (generatedCount % 100000 == 0
                        || generatedCount == rows) {

                    System.out.println(
                            "Generated "
                                    + generatedCount
                                    + " records"
                    );
                }
            }
        }

        double executionTimeSeconds =
                (System.currentTimeMillis() - startTime)
                        / 1000.0;

        System.out.println();
        System.out.println(
                "Generation completed successfully"
        );
        System.out.println(
                "Rows generated : " + rows
        );
        System.out.println(
                "Output path    : "
                        + outputDir.toAbsolutePath()
        );
        System.out.println(
                "Execution time : "
                        + executionTimeSeconds
                        + " seconds"
        );
    }
}