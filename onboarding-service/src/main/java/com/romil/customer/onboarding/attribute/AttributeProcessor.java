package com.romil.customer.onboarding.attribute;

import com.romil.customer.onboarding.customer360.Customer360;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class AttributeProcessor {

    public Map<String, String> deriveAttributes(Customer360 customer) {

        Map<String, String> attributes = new HashMap<>();

        attributes.put(
                "income_band",
                getIncomeBand(customer.getIncome())
        );

        attributes.put(
                "customer_segment",
                getCustomerSegment(customer.getIncome())
        );

        attributes.put(
                "spend_band",
                getSpendBand(customer.getMonthlySpend())
        );

        attributes.put(
                "credit_band",
                getCreditBand(customer.getCreditScore())
        );

        return attributes;
    }

    private String getIncomeBand(Double income) {

        if (income == null) {
            return "UNKNOWN";
        }

        if (income > 100000) {
            return "HIGH";
        }

        if (income >= 50000) {
            return "MEDIUM";
        }

        return "LOW";
    }

    private String getCustomerSegment(Double income) {

        if (income == null) {
            return "UNKNOWN";
        }

        if (income > 150000) {
            return "PREMIUM";
        }

        if (income >= 75000) {
            return "STANDARD";
        }

        return "BASIC";
    }

    private String getSpendBand(Double monthlySpend) {

        if (monthlySpend == null) {
            return "UNKNOWN";
        }

        if (monthlySpend > 50000) {
            return "HIGH_SPENDER";
        }

        if (monthlySpend >= 20000) {
            return "MEDIUM_SPENDER";
        }

        return "LOW_SPENDER";
    }

    private String getCreditBand(Integer creditScore) {

        if (creditScore == null) {
            return "UNKNOWN";
        }

        if (creditScore > 750) {
            return "EXCELLENT";
        }

        if (creditScore >= 650) {
            return "GOOD";
        }

        return "POOR";
    }
}
