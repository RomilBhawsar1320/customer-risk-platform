package com.romil.customer.onboarding.model;

import lombok.Data;

@Data
public class RiskResult {

    private String customerId;
    private String customerName;

    private Integer creditScore;
    private Double income;

    private String riskCategory;
    private String offer;

    private Integer creditLimit;

}