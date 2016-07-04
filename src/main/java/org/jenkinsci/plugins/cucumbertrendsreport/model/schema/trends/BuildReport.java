package org.jenkinsci.plugins.cucumbertrendsreport.model.schema.trends;

/**
 * Created by Thanh Q. Le
 * Date: 20/06/16
 * Time: 8:59 AM
 */
public class BuildReport {
    private Integer buildNumber;
    private String buildTime;
    private Integer numberOfScenarios;
    private Double failRate;

    public Integer getBuildNumber() {
        return buildNumber;
    }

    public void setBuildNumber(Integer buildNumber) {
        this.buildNumber = buildNumber;
    }

    public String getBuildTime() {
        return buildTime;
    }

    public void setBuildTime(String buildTime) {
        this.buildTime = buildTime;
    }

    public Integer getNumberOfScenarios() {
        return numberOfScenarios;
    }

    public void setNumberOfScenarios(Integer numberOfScenarios) {
        this.numberOfScenarios = numberOfScenarios;
    }

    public Double getFailRate() {
        return failRate;
    }

    public void setFailRate(Double failRate) {
        this.failRate = failRate;
    }
}
