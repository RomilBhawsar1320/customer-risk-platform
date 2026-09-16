package com.romil.customer.spark.generator;

import java.util.Random;

public final class RandomDataFactory {

    private static final Random RANDOM = new Random();

    private static final String[] PRODUCTS = {
            "CREDIT_CARD",
            "HOME_LOAN",
            "PERSONAL_LOAN",
            "AUTO_LOAN"
    };

    private RandomDataFactory() {
    }

    public static CustomerData generate(
            long customerId
    ) {

        String customerName =
                "Customer_" + customerId;

        int age =
                21 + RANDOM.nextInt(40);

        double income =
                25000 + RANDOM.nextInt(225000);

        int creditScore;

        if (income > 150000) {

            creditScore =
                    760 + RANDOM.nextInt(91);

        } else if (income > 80000) {

            creditScore =
                    680 + RANDOM.nextInt(81);

        } else {

            creditScore =
                    550 + RANDOM.nextInt(131);
        }

        String bureauStatus =
                creditScore >= 700
                        ? "GOOD"
                        : "REVIEW";

        double monthlySpend =
                income * (0.15 + RANDOM.nextDouble() * 0.25);

        double avgBalance =
                income * (0.50 + RANDOM.nextDouble());

        String productName =
                PRODUCTS[RANDOM.nextInt(PRODUCTS.length)];

        boolean emailOptIn =
                RANDOM.nextDouble() < 0.7;

        return new CustomerData(
                customerId,
                customerName,
                age,
                income,
                creditScore,
                bureauStatus,
                monthlySpend,
                avgBalance,
                productName,
                emailOptIn
        );
    }
}