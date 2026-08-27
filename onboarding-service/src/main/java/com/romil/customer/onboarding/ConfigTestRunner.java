package com.romil.customer.onboarding;

import com.romil.customer.onboarding.config.ConfigLoader;
import com.romil.customer.onboarding.config.PipelineConfig;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ConfigTestRunner implements CommandLineRunner {

    private final ConfigLoader configLoader;

    public ConfigTestRunner(ConfigLoader configLoader) {
        this.configLoader = configLoader;
    }

    @Override
    public void run(String... args) {

        PipelineConfig config =
                configLoader.loadConfig(
                        "src/main/resources/config/pipeline-config.json"
                );

        System.out.println("=========== PIPELINE CONFIG ===========");
        System.out.println(config);
        System.out.println("=======================================");
    }
}