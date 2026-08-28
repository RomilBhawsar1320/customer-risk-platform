package com.romil.customer.onboarding.customer360;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Customer360Builder {

    public Map<String, Customer360> build(
            List<Map<String, String>> customers,
            List<Map<String, String>> bureau,
            List<Map<String, String>> transactions,
            List<Map<String, String>> products,
            List<Map<String, String>> marketing) {

        Map<String, Customer360> customer360Map = new HashMap<>();

        // Customer Master
        for (Map<String, String> row : customers) {

            Customer360 customer = new Customer360();

            customer.setCustomerId(row.get("customer_id"));
            customer.setCustomerName(row.get("customer_name"));
            customer.setAge(Integer.parseInt(row.get("age")));
            customer.setIncome(Double.parseDouble(row.get("income")));

            customer360Map.put(
                    customer.getCustomerId(),
                    customer
            );
        }

        // Bureau
        for (Map<String, String> row : bureau) {

            Customer360 customer =
                    customer360Map.get(row.get("customer_id"));

            if (customer != null) {

                customer.setCreditScore(
                        Integer.parseInt(
                                row.get("credit_score")
                        )
                );

                customer.setBureauStatus(
                        row.get("bureau_status")
                );
            }
        }

        // Transactions
        for (Map<String, String> row : transactions) {

            Customer360 customer =
                    customer360Map.get(row.get("customer_id"));

            if (customer != null) {

                customer.setMonthlySpend(
                        Double.parseDouble(
                                row.get("monthly_spend")
                        )
                );

                customer.setAvgBalance(
                        Double.parseDouble(
                                row.get("avg_balance")
                        )
                );
            }
        }

        // Products
        for (Map<String, String> row : products) {

            Customer360 customer =
                    customer360Map.get(row.get("customer_id"));

            if (customer != null) {

                customer.setProductName(
                        row.get("product_name")
                );
            }
        }

        // Marketing
        for (Map<String, String> row : marketing) {

            Customer360 customer =
                    customer360Map.get(row.get("customer_id"));

            if (customer != null) {

                customer.setEmailOptIn(
                        Boolean.parseBoolean(
                                row.get("email_opt_in")
                        )
                );
            }
        }

        return customer360Map;
    }
}