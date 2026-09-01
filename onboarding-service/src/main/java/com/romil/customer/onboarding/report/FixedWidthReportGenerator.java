package com.romil.customer.onboarding.report;

import com.romil.customer.onboarding.model.RiskResult;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.util.List;

@Component
public class FixedWidthReportGenerator {

    public void generate(
            List<RiskResult> results,
            String fileName) {

        try (PrintWriter writer =
                     new PrintWriter(fileName)) {

            writer.println(
                    String.format(
                            "%-10s%-20s%-15s%-20s%-12s",
                            "CUSTOMER",
                            "NAME",
                            "RISK",
                            "OFFER",
                            "LIMIT"
                    )
            );

            for (RiskResult result : results) {

                writer.println(
                        String.format(
                                "%-10s%-20s%-15s%-20s%-12s",
                                result.getCustomerId(),
                                result.getCustomerName(),
                                result.getRiskCategory(),
                                result.getOffer(),
                                result.getCreditLimit()
                        )
                );
            }

            System.out.println(
                    "Fixed Width Report Generated : "
                            + fileName);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}