package com.romil.customer.onboarding;

import com.romil.customer.onboarding.common.PipelineExecutor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ConfigTestRunner implements CommandLineRunner {

    private final PipelineExecutor pipelineExecutor;

    public ConfigTestRunner(
            PipelineExecutor pipelineExecutor) {
        this.pipelineExecutor = pipelineExecutor;
    }

    @Override
    public void run(String... args) {

        pipelineExecutor.execute();
    }
}