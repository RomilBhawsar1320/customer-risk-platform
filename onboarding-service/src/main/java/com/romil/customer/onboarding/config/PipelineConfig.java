package com.romil.customer.onboarding.config;

import lombok.Data;

import java.util.List;

@Data
public class PipelineConfig {

    private List<DatasetConfig> datasets;

    private List<JoinConfig> joins;

    private List<FilterConfig> filters;

    private List<RiskRuleConfig> riskRules;
}