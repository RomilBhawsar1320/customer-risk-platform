package com.romil.customer.spark.config;

public class AttributeRulesConfig {

    private IncomeBandConfig incomeBand;
    private CustomerSegmentConfig customerSegment;
    private SpendBandConfig spendBand;
    private CreditBandConfig creditBand;

    public IncomeBandConfig getIncomeBand() {
        return incomeBand;
    }

    public void setIncomeBand(IncomeBandConfig incomeBand) {
        this.incomeBand = incomeBand;
    }

    public CustomerSegmentConfig getCustomerSegment() {
        return customerSegment;
    }

    public void setCustomerSegment(CustomerSegmentConfig customerSegment) {
        this.customerSegment = customerSegment;
    }

    public SpendBandConfig getSpendBand() {
        return spendBand;
    }

    public void setSpendBand(SpendBandConfig spendBand) {
        this.spendBand = spendBand;
    }

    public CreditBandConfig getCreditBand() {
        return creditBand;
    }

    public void setCreditBand(CreditBandConfig creditBand) {
        this.creditBand = creditBand;
    }
}