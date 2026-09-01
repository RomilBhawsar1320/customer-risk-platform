package com.romil.customer.onboarding.report;

import com.romil.customer.onboarding.model.RiskResult;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.util.List;

@Component
public class CsvReportGenerator {

    public void generate(
            List<RiskResult> results,
            String outputPath) {

        try (FileWriter writer = new FileWriter(outputPath)) {

            writer.write(
                    "customer_id,customer_name,credit_score,income,risk_category,offer,credit_limit\n"
            );

            for (RiskResult result : results) {

                writer.write(
                        result.getCustomerId() + "," +
                                result.getCustomerName() + "," +
                                result.getCreditScore() + "," +
                                result.getIncome() + "," +
                                result.getRiskCategory() + "," +
                                result.getOffer() + "," +
                                result.getCreditLimit() +
                                "\n"
                );
            }

            System.out.println(
                    "Report generated successfully : "
                            + outputPath);

        } catch (Exception e) {

            throw new RuntimeException(
                    "Error generating report",
                    e
            );
        }
    }
}