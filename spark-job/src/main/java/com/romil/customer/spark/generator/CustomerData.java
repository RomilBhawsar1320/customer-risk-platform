package com.romil.customer.spark.generator;

public record CustomerData(

        long customerId,
        String customerName,
        int age,
        double income,

        int creditScore,
        String bureauStatus,

        double monthlySpend,
        double avgBalance,

        String productName,

        boolean emailOptIn

) {
}