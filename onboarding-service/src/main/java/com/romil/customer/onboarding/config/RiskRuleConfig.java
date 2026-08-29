package com.romil.customer.onboarding.config;

import lombok.Data;

@Data
public class RiskRuleConfig {

    private String riskCategory;
    private String offer;

    private Integer minCreditScore;
    private Integer maxCreditScore;

    private Double minIncome;
    private Double maxIncome;
}