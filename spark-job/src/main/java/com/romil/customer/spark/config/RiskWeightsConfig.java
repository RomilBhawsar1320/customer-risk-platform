package com.romil.customer.spark.config;

public class RiskWeightsConfig {

    private double creditScoreWeight;
    private double incomeDivisor;
    private double balanceDivisor;

    public double getCreditScoreWeight() {
        return creditScoreWeight;
    }

    public void setCreditScoreWeight(
            double creditScoreWeight) {

        this.creditScoreWeight = creditScoreWeight;
    }

    public double getIncomeDivisor() {
        return incomeDivisor;
    }

    public void setIncomeDivisor(
            double incomeDivisor) {

        this.incomeDivisor = incomeDivisor;
    }

    public double getBalanceDivisor() {
        return balanceDivisor;
    }

    public void setBalanceDivisor(
            double balanceDivisor) {

        this.balanceDivisor = balanceDivisor;
    }
}