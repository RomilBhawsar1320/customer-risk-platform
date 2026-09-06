package com.romil.customer.spark.config;

public class CustomerSegmentConfig {

    private int premium;
    private int standard;

    public int getPremium() {
        return premium;
    }

    public void setPremium(int premium) {
        this.premium = premium;
    }

    public int getStandard() {
        return standard;
    }

    public void setStandard(int standard) {
        this.standard = standard;
    }
}