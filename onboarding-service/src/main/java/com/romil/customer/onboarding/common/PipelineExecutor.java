package com.romil.customer.onboarding.common;

import com.romil.customer.onboarding.attribute.AttributeProcessor;
import com.romil.customer.onboarding.config.ConfigLoader;
import com.romil.customer.onboarding.config.PipelineConfig;
import com.romil.customer.onboarding.customer360.Customer360;
import com.romil.customer.onboarding.customer360.Customer360Builder;
import com.romil.customer.onboarding.model.RiskResult;
import com.romil.customer.onboarding.model.RiskScoringEngine;
import com.romil.customer.onboarding.reader.CsvReaderService;
import com.romil.customer.onboarding.report.CsvReportGenerator;
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
    private RiskScoringEngine riskScoringEngine;

    @Autowired
    private CsvReportGenerator csvReportGenerator;

    public void execute() {

        System.out.println("=================================");
        System.out.println("Starting Customer Risk Pipeline");
        System.out.println("=================================");

        PipelineConfig config =
                configLoader.loadConfig("pipeline-config.json");

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

        Map<String, Customer360> customer360Map =
                customer360Builder.build(
                        customers,
                        bureau,
                        transactions,
                        products,
                        marketing
                );

        List<RiskResult> results = new ArrayList<>();

        for (Customer360 customer : customer360Map.values()) {

            attributeProcessor.deriveAttributes(customer);

            RiskResult riskResult =
                    riskScoringEngine.evaluate(customer);

            results.add(riskResult);
        }

        csvReportGenerator.generate(
                results,
                "customer-risk-report.csv"
        );

        System.out.println("=================================");
        System.out.println("Pipeline Execution Completed");
        System.out.println("Records Processed : "
                + customer360Map.size());
        System.out.println("Report Generated : customer-risk-report.csv");
        System.out.println("=================================");
    }
}


/* Think of it as your OneTru workflow engine.
1. Load Config
2. Read CSV Data
3. Build Customer360
4. Apply Filters
5. Generate Attributes
6. Calculate Risk
7. Generate Report
 */
