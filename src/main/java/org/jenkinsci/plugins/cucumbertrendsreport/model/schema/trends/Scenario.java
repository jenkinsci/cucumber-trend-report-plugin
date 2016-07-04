package org.jenkinsci.plugins.cucumbertrendsreport.model.schema.trends;

/**
 * Created by Thanh Q. Le
 * Date: 22/06/16
 * Time: 3:50 PM
 */
public class Scenario {
    private String scenarioName;
    private Integer passed;
    private Integer failed;

    public String getScenarioName() {
        return scenarioName;
    }

    public void setScenarioName(String scenarioName) {
        this.scenarioName = scenarioName;
    }

    public Integer getPassed() {
        return passed;
    }

    public void setPassed(Integer passed) {
        this.passed = passed;
    }

    public Integer getFailed() {
        return failed;
    }

    public void setFailed(Integer failed) {
        this.failed = failed;
    }
}
