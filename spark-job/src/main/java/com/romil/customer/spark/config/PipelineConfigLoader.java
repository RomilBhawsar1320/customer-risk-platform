package com.romil.customer.spark.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;

public class PipelineConfigLoader {

    public static PipelineConfig load(String configFile) {

        try {

            ObjectMapper mapper =
                    new ObjectMapper();

            return mapper.readValue(
                    PipelineConfigLoader.class
                            .getClassLoader()
                            .getResourceAsStream(configFile),
                    PipelineConfig.class
            );

        } catch (Exception e) {

            throw new RuntimeException(
                    "Unable to load pipeline configuration",
                    e
            );
        }
    }
}