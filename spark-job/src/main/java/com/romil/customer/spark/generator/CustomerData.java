package com.romil.customer.spark.generator;

public record CustomerData(

        long customerId,
        String customerName,
        int age,
        double income,

        String occupation,
        String employmentType,
        int employmentTenureMonths,
        String city,
        String state,
        String maritalStatus,
        int dependents,
        String educationLevel,
        int accountAgeMonths,

        int creditScore,
        String bureauStatus,

        double monthlySpend,
        double avgBalance,

        String productName,

        boolean emailOptIn,

        double creditUtilizationPct,
        int missedPayments12m,
        double outstandingLoanAmount,

        double emiOutflow,
        boolean mobileAppActiveFlag

) {
}