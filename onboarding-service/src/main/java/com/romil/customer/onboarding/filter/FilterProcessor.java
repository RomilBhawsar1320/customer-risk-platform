package com.romil.customer.onboarding.filter;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class FilterProcessor {

    public List<Map<String, String>> applyCreditScoreFilter(
            List<Map<String, String>> input,
            int minimumScore) {

        List<Map<String, String>> filtered = new ArrayList<>();

        for (Map<String, String> row : input) {

            Integer score =
                    Integer.parseInt(
                            row.get("credit_score")
                    );

            if (score > minimumScore) {

                filtered.add(row);
            }
        }

        return filtered;
    }
}