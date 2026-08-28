package com.romil.customer.onboarding.customer360;

import lombok.Data;

@Data
public class Customer360 {

    private String customerId;
    private String customerName;

    private Integer age;
    private Double income;

    private Integer creditScore;
    private String bureauStatus;

    private Double monthlySpend;
    private Double avgBalance;

    private String productName;

    private Boolean emailOptIn;
}