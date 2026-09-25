package com.romil.customer.spark.generator;

import java.util.Random;

public final class RandomDataFactory {

    private static final Random RANDOM = new Random(42);

    private static final String[] PRODUCTS = {
            "CREDIT_CARD",
            "HOME_LOAN",
            "PERSONAL_LOAN",
            "AUTO_LOAN"
    };

    private static final String[][] LOCATIONS = {
            {"Pune", "Maharashtra"},
            {"Mumbai", "Maharashtra"},
            {"Bengaluru", "Karnataka"},
            {"Hyderabad", "Telangana"},
            {"Chennai", "Tamil_Nadu"},
            {"Delhi", "Delhi"},
            {"Ahmedabad", "Gujarat"},
            {"Kolkata", "West_Bengal"}
    };

    private static final String[] EDUCATION_LEVELS = {
            "HIGH_SCHOOL",
            "DIPLOMA",
            "GRADUATE",
            "POST_GRADUATE",
            "PROFESSIONAL"
    };

    private RandomDataFactory() {
    }

    public static CustomerData generate(long customerId) {

        String customerName =
                "Customer_" + customerId;

        int age =
                21 + RANDOM.nextInt(40);

        String employmentType =
                generateEmploymentType();

        String occupation =
                generateOccupation(employmentType);

        String educationLevel =
                generateEducationLevel(occupation);

        double income =
                generateIncome(
                        employmentType,
                        occupation
                );

        int maximumEmploymentTenureMonths =
                Math.max(
                        1,
                        (age - 18) * 12
                );

        int employmentTenureMonths =
                1 + RANDOM.nextInt(
                        maximumEmploymentTenureMonths
                );

        String[] location =
                LOCATIONS[
                        RANDOM.nextInt(LOCATIONS.length)
                        ];

        String city = location[0];
        String state = location[1];

        String maritalStatus =
                generateMaritalStatus(age);

        int dependents =
                generateDependents(
                        age,
                        maritalStatus
                );

        int maximumAccountAgeMonths =
                Math.max(
                        1,
                        (age - 18) * 12
                );

        int accountAgeMonths =
                1 + RANDOM.nextInt(
                        maximumAccountAgeMonths
                );

        int creditScore =
                generateCreditScore(
                        income,
                        employmentTenureMonths,
                        accountAgeMonths
                );

        String bureauStatus =
                creditScore >= 700
                        ? "GOOD"
                        : "REVIEW";

        double monthlySpend =
                income
                        * (0.15
                        + RANDOM.nextDouble() * 0.25);

        double avgBalance =
                income
                        * (0.50
                        + RANDOM.nextDouble());

        String productName =
                PRODUCTS[
                        RANDOM.nextInt(PRODUCTS.length)
                        ];

        boolean emailOptIn =
                RANDOM.nextDouble() < 0.70;

        double creditUtilizationPct =
                generateCreditUtilizationPct(
                        creditScore,
                        bureauStatus
                );

        int missedPayments12m =
                generateMissedPayments12m(
                        creditScore,
                        bureauStatus,
                        creditUtilizationPct
                );

        double outstandingLoanAmount =
                generateOutstandingLoanAmount(
                        income,
                        creditScore,
                        creditUtilizationPct
                );

        double emiOutflow =
                generateEmiOutflow(
                        outstandingLoanAmount,
                        income,
                        creditScore
                );

        boolean mobileAppActiveFlag =
                RANDOM.nextDouble() < 0.75;

        return new CustomerData(
                customerId,
                customerName,
                age,
                income,
                occupation,
                employmentType,
                employmentTenureMonths,
                city,
                state,
                maritalStatus,
                dependents,
                educationLevel,
                accountAgeMonths,
                creditScore,
                bureauStatus,
                monthlySpend,
                avgBalance,
                productName,
                emailOptIn,
                creditUtilizationPct,
                missedPayments12m,
                outstandingLoanAmount,
                emiOutflow,
                mobileAppActiveFlag
        );
    }

    private static String generateEmploymentType() {

        double value = RANDOM.nextDouble();

        if (value < 0.65) {
            return "SALARIED";
        }

        if (value < 0.85) {
            return "SELF_EMPLOYED";
        }

        if (value < 0.95) {
            return "BUSINESS";
        }

        return "CONTRACT";
    }

    private static String generateOccupation(
            String employmentType
    ) {

        String[] occupations;

        switch (employmentType) {

            case "SALARIED":
                occupations = new String[]{
                        "SOFTWARE_ENGINEER",
                        "BANKER",
                        "TEACHER",
                        "ACCOUNTANT",
                        "SALES_MANAGER",
                        "OPERATIONS_MANAGER"
                };
                break;

            case "SELF_EMPLOYED":
                occupations = new String[]{
                        "CONSULTANT",
                        "DOCTOR",
                        "LAWYER",
                        "ARCHITECT",
                        "FREELANCER"
                };
                break;

            case "BUSINESS":
                occupations = new String[]{
                        "BUSINESS_OWNER",
                        "TRADER",
                        "RETAILER",
                        "MANUFACTURER"
                };
                break;

            default:
                occupations = new String[]{
                        "CONTRACTOR",
                        "TECHNICIAN",
                        "FIELD_EXECUTIVE"
                };
        }

        return occupations[
                RANDOM.nextInt(occupations.length)
                ];
    }

    private static String generateEducationLevel(
            String occupation
    ) {

        if (occupation.equals("DOCTOR")
                || occupation.equals("LAWYER")
                || occupation.equals("ARCHITECT")) {

            return RANDOM.nextBoolean()
                    ? "POST_GRADUATE"
                    : "PROFESSIONAL";
        }

        if (occupation.equals("SOFTWARE_ENGINEER")
                || occupation.equals("BANKER")
                || occupation.equals("ACCOUNTANT")) {

            return RANDOM.nextBoolean()
                    ? "GRADUATE"
                    : "POST_GRADUATE";
        }

        return EDUCATION_LEVELS[
                RANDOM.nextInt(
                        EDUCATION_LEVELS.length
                )
                ];
    }

    private static double generateIncome(
            String employmentType,
            String occupation
    ) {

        int minimumIncome;
        int incomeRange;

        switch (employmentType) {

            case "BUSINESS":
                minimumIncome = 100000;
                incomeRange = 300000;
                break;

            case "SELF_EMPLOYED":
                minimumIncome = 60000;
                incomeRange = 240000;
                break;

            case "SALARIED":
                minimumIncome = 40000;
                incomeRange = 210000;
                break;

            default:
                minimumIncome = 25000;
                incomeRange = 100000;
        }

        if (occupation.equals("DOCTOR")
                || occupation.equals("BUSINESS_OWNER")
                || occupation.equals("ARCHITECT")) {

            minimumIncome += 50000;
        }

        return minimumIncome
                + RANDOM.nextInt(incomeRange);
    }

    private static String generateMaritalStatus(
            int age
    ) {

        if (age < 25) {
            return RANDOM.nextDouble() < 0.85
                    ? "SINGLE"
                    : "MARRIED";
        }

        if (age < 35) {
            return RANDOM.nextDouble() < 0.55
                    ? "MARRIED"
                    : "SINGLE";
        }

        double value = RANDOM.nextDouble();

        if (value < 0.75) {
            return "MARRIED";
        }

        if (value < 0.90) {
            return "SINGLE";
        }

        return "DIVORCED";
    }

    private static int generateDependents(
            int age,
            String maritalStatus
    ) {

        if (maritalStatus.equals("SINGLE")) {
            return RANDOM.nextDouble() < 0.80
                    ? 0
                    : 1;
        }

        if (age < 30) {
            return RANDOM.nextInt(2);
        }

        if (age < 45) {
            return RANDOM.nextInt(4);
        }

        return RANDOM.nextInt(5);
    }

    private static int generateCreditScore(
            double income,
            int employmentTenureMonths,
            int accountAgeMonths
    ) {

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

        if (employmentTenureMonths >= 60) {
            creditScore += 10;
        }

        if (accountAgeMonths >= 48) {
            creditScore += 10;
        }

        return Math.min(
                creditScore,
                850
        );
    }

    private static double generateCreditUtilizationPct(
            int creditScore,
            String bureauStatus
    ) {

        double base =
                bureauStatus.equals("GOOD")
                        ? 25.0 + RANDOM.nextDouble() * 35.0
                        : 35.0 + RANDOM.nextDouble() * 50.0;

        if (creditScore >= 750) {
            base *= 0.8;
        } else if (creditScore < 650) {
            base *= 1.2;
        }

        return Math.min(
                100.0,
                Math.max(0.0, base)
        );
    }

    private static int generateMissedPayments12m(
            int creditScore,
            String bureauStatus,
            double creditUtilizationPct
    ) {

        int upperBound =
                bureauStatus.equals("GOOD")
                        ? 3
                        : 12;

        if (creditScore >= 750) {
            upperBound = 2;
        }

        if (creditUtilizationPct > 80.0) {
            upperBound += 4;
        }

        return RANDOM.nextInt(upperBound + 1);
    }

    private static double generateOutstandingLoanAmount(
            double income,
            int creditScore,
            double creditUtilizationPct
    ) {

        double base =
                income * (0.10 + RANDOM.nextDouble() * 0.55);

        if (creditScore >= 750) {
            base *= 0.8;
        } else if (creditScore < 650) {
            base *= 1.3;
        }

        if (creditUtilizationPct > 80.0) {
            base *= 1.2;
        }

        return Math.max(0.0, base);
    }

    private static double generateEmiOutflow(
            double outstandingLoanAmount,
            double income,
            int creditScore
    ) {

        double base =
                outstandingLoanAmount
                        * (0.08 + RANDOM.nextDouble() * 0.18);

        if (creditScore >= 750) {
            base *= 0.9;
        } else if (creditScore < 650) {
            base *= 1.2;
        }

        if (income > 0) {
            base = Math.min(base, income * 0.6);
        }

        return Math.max(0.0, base);
    }
}