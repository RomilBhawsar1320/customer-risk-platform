package com.romil.customer.onboarding.model;

import com.romil.customer.onboarding.customer360.Customer360;
import org.springframework.stereotype.Component;

@Component
public class RiskScoringEngine {

    public RiskResult evaluate(Customer360 customer) {

        RiskResult result = new RiskResult();

        result.setCustomerId(customer.getCustomerId());
        result.setCustomerName(customer.getCustomerName());
        result.setCreditScore(customer.getCreditScore());
        result.setIncome(customer.getIncome());

        int score = customer.getCreditScore();

        if (score > 750 && customer.getIncome() > 100000) {

            result.setRiskCategory("LOW_RISK");
            result.setOffer("PLATINUM_CARD");

        } else if (score >= 650) {

            result.setRiskCategory("MEDIUM_RISK");
            result.setOffer("GOLD_CARD");

        } else {

            result.setRiskCategory("HIGH_RISK");
            result.setOffer("NO_OFFER");
        }

        return result;
    }
}