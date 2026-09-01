package com.romil.customer.onboarding.common;

import com.romil.customer.onboarding.attribute.AttributeProcessor;
import com.romil.customer.onboarding.config.ConfigLoader;
import com.romil.customer.onboarding.config.PipelineConfig;
import com.romil.customer.onboarding.customer360.Customer360;
import com.romil.customer.onboarding.customer360.Customer360Builder;
import com.romil.customer.onboarding.fulfillment.BatchFulfillmentService;
import com.romil.customer.onboarding.model.ConfigDrivenRiskScoringEngine;
import com.romil.customer.onboarding.model.RiskResult;
import com.romil.customer.onboarding.reader.CsvReaderService;
import com.romil.customer.onboarding.report.CsvReportGenerator;
import com.romil.customer.onboarding.report.FixedWidthReportGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class PipelineExecutor {

    @Autowired
    private ConfigLoader configLoader;

    @Autowired
    private CsvReaderService csvReaderService;

    @Autowired
    private Customer360Builder customer360Builder;

    @Autowired
    private AttributeProcessor attributeProcessor;

    @Autowired
    private ConfigDrivenRiskScoringEngine riskScoringEngine;

    @Autowired
    private CsvReportGenerator csvReportGenerator;

    @Autowired
    private FixedWidthReportGenerator fixedWidthReportGenerator;

    @Autowired
    private BatchFulfillmentService batchFulfillmentService;

    public void execute() {

        System.out.println("=================================");
        System.out.println("Starting Customer Risk Pipeline");
        System.out.println("=================================");

        // Step 1 : Load Configuration
        PipelineConfig config =
                configLoader.loadConfig("pipeline-config.json");

        System.out.println("Configuration Loaded Successfully");

        // Step 2 : Load Source Datasets
        List<Map<String, String>> customers =
                csvReaderService.read("../sample-data/customer_master.csv");

        List<Map<String, String>> bureau =
                csvReaderService.read("../sample-data/credit_bureau.csv");

        List<Map<String, String>> transactions =
                csvReaderService.read("../sample-data/transaction_summary.csv");

        List<Map<String, String>> products =
                csvReaderService.read("../sample-data/product_holdings.csv");

        List<Map<String, String>> marketing =
                csvReaderService.read("../sample-data/marketing_preferences.csv");

        System.out.println("Source Data Loaded Successfully");

        // Step 3 : Build Customer360
        Map<String, Customer360> customer360Map =
                customer360Builder.build(
                        customers,
                        bureau,
                        transactions,
                        products,
                        marketing
                );

        System.out.println(
                "Customer360 Created : "
                        + customer360Map.size()
                        + " records");

        // Step 4 : Execute Risk Model
        List<RiskResult> results = new ArrayList<>();

        for (Customer360 customer : customer360Map.values()) {

            attributeProcessor.deriveAttributes(customer);

            RiskResult riskResult =
                    riskScoringEngine.evaluate(
                            customer,
                            config);

            results.add(riskResult);
        }

        System.out.println("Risk Assessment Completed");

        // Step 5 : CSV Report Generation
        csvReportGenerator.generate(
                results,
                "customer-risk-report.csv"
        );

        System.out.println(
                "CSV Report Generated : customer-risk-report.csv");

        // Step 6 : Fixed Width Report Generation
        fixedWidthReportGenerator.generate(
                results,
                "customer-offer-fixed-width.txt"
        );

        // Step 7 : Batch Fulfillment
        batchFulfillmentService.execute();

        System.out.println("=================================");
        System.out.println("Pipeline Execution Completed");
        System.out.println("Records Processed : "
                + customer360Map.size());
        System.out.println("CSV Report : customer-risk-report.csv");
        System.out.println("Fixed Width Report : customer-offer-fixed-width.txt");
        System.out.println("Batch Fulfillment : COMPLETED");
        System.out.println("=================================");
    }
}

/*
OneTru Workflow

1. Load Config
2. Read CSV Data
3. Build Customer360
4. Generate Derived Attributes
5. Execute Risk Model
6. Generate Credit Offers
7. Generate CSV Report
8. Generate Fixed Width Output
9. Batch Fulfillment
10. Completed

*/