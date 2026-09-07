package com.romil.customer.spark.config;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public class PipelineConfig {

    private List<DatasetConfig> datasets;
    private OutputConfig output;

    public List<DatasetConfig> getDatasets() {
        return datasets;
    }

    public void setDatasets(List<DatasetConfig> datasets) {
        this.datasets = datasets;
    }

    public OutputConfig getOutput() {
        return output;
    }

    public void setOutput(OutputConfig output) {
        this.output = output;
    }

    private AttributeRulesConfig attributeRules;

    public AttributeRulesConfig getAttributeRules() {
        return attributeRules;
    }

    public void setAttributeRules(
            AttributeRulesConfig attributeRules) {
        this.attributeRules = attributeRules;
    }
    private RiskWeightsConfig riskWeights;
    public RiskWeightsConfig getRiskWeights() {
        return riskWeights;
    }

    public void setRiskWeights(
            RiskWeightsConfig riskWeights) {

        this.riskWeights = riskWeights;
    }
    private List<RiskRuleConfig> riskRules;
    public List<RiskRuleConfig> getRiskRules() {
        return riskRules;
    }

    public void setRiskRules(
            List<RiskRuleConfig> riskRules) {

        this.riskRules = riskRules;
    }
    private List<JoinConfig> joins;

    public List<JoinConfig> getJoins() {
        return joins;
    }

    public void setJoins(List<JoinConfig> joins) {
        this.joins = joins;
    }
}

