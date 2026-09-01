package com.romil.customer.onboarding.fulfillment;

import org.springframework.stereotype.Component;

@Component
public class BatchFulfillmentService {

    public void execute() {

        System.out.println("=================================");
        System.out.println("Batch Fulfillment Started");
        System.out.println("=================================");

        System.out.println(
                "Delivering customer-risk-report.csv");

        System.out.println(
                "Delivering customer-offer-fixed-width.txt");

        System.out.println(
                "Batch Fulfillment Completed");

        System.out.println("=================================");
    }
}