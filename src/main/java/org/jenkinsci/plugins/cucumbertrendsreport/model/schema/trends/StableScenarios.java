package org.jenkinsci.plugins.cucumbertrendsreport.model.schema.trends;

/**
 * Created by Thanh Q. Le
 * Date: 22/06/16
 * Time: 3:50 PM
 */
public class StableScenarios {
    private String scenarioName;
    private Double failRate;
    private Integer executedTime;

    public String getScenarioName() {
        return scenarioName;
    }

    public void setScenarioName(String scenarioName) {
        this.scenarioName = scenarioName;
    }

    public Double getFailRate() {
        return failRate;
    }

    public void setFailRate(Double failRate) {
        this.failRate = failRate;
    }

    public Integer getExecutedTime() {
        return executedTime;
    }

    public void setExecutedTime(Integer executedTime) {
        this.executedTime = executedTime;
    }
}
