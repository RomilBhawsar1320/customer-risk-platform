package com.romil.customer.onboarding.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class ConfigLoader {

    public PipelineConfig loadConfig(String s) {

        try {

            ObjectMapper mapper = new ObjectMapper();

            return mapper.readValue(
                    new ClassPathResource(
                            "config/pipeline-config.json"
                    ).getInputStream(),
                    PipelineConfig.class
            );

        } catch (Exception e) {

            throw new RuntimeException(
                    "Failed to load pipeline configuration",
                    e
            );
        }
    }
}