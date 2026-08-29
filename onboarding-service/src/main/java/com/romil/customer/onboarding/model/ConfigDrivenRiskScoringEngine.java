package com.romil.customer.onboarding.model;

import com.romil.customer.onboarding.config.PipelineConfig;
import com.romil.customer.onboarding.config.RiskRuleConfig;
import com.romil.customer.onboarding.customer360.Customer360;
import org.springframework.stereotype.Component;

@Component
public class ConfigDrivenRiskScoringEngine {

    public RiskResult evaluate(
            Customer360 customer,
            PipelineConfig config) {

        for (RiskRuleConfig rule : config.getRiskRules()) {

            boolean scoreMatch =
                    (rule.getMinCreditScore() == null
                            || customer.getCreditScore() >= rule.getMinCreditScore())
                            &&
                            (rule.getMaxCreditScore() == null
                                    || customer.getCreditScore() <= rule.getMaxCreditScore());

            boolean incomeMatch =
                    (rule.getMinIncome() == null
                            || customer.getIncome() >= rule.getMinIncome())
                            &&
                            (rule.getMaxIncome() == null
                                    || customer.getIncome() <= rule.getMaxIncome());

            if (scoreMatch && incomeMatch) {

                RiskResult result = new RiskResult();

                result.setCustomerId(
                        customer.getCustomerId());

                result.setCustomerName(
                        customer.getCustomerName());

                result.setCreditScore(
                        customer.getCreditScore());

                result.setIncome(
                        customer.getIncome());

                result.setRiskCategory(
                        rule.getRiskCategory());

                result.setOffer(
                        rule.getOffer());

                return result;
            }
        }

        RiskResult defaultResult = new RiskResult();

        defaultResult.setCustomerId(
                customer.getCustomerId());

        defaultResult.setCustomerName(
                customer.getCustomerName());

        defaultResult.setCreditScore(
                customer.getCreditScore());

        defaultResult.setIncome(
                customer.getIncome());

        defaultResult.setRiskCategory(
                "UNKNOWN");

        defaultResult.setOffer(
                "NO_OFFER");

        return defaultResult;
    }
}