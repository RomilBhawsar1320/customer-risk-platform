package com.romil.customer.spark.config;

import java.util.Map;
import java.util.LinkedHashMap;

public class AttributeRulesConfig {

    private LinkedHashMap<String,String> incomeBand;
    private LinkedHashMap<String,String> customerSegment;
    private LinkedHashMap<String,String> spendBand;
    private LinkedHashMap<String,String> creditBand;

    public Map<String, String> getIncomeBand() {
        return incomeBand;
    }

    public void setIncomeBand(Map<String, String> incomeBand) {
        this.incomeBand = (LinkedHashMap<String, String>) incomeBand;
    }

    public Map<String, String> getCustomerSegment() {
        return customerSegment;
    }

    public void setCustomerSegment(Map<String, String> customerSegment) {
        this.customerSegment = (LinkedHashMap<String, String>) customerSegment;
    }

    public Map<String, String> getSpendBand() {
        return spendBand;
    }

    public void setSpendBand(Map<String, String> spendBand) {
        this.spendBand = (LinkedHashMap<String, String>) spendBand;
    }

    public Map<String, String> getCreditBand() {
        return creditBand;
    }

    public void setCreditBand(Map<String, String> creditBand) {
        this.creditBand = (LinkedHashMap<String, String>) creditBand;
    }
}