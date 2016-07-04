package org.jenkinsci.plugins.cucumbertrendsreport.model.schema.cucumber;

import java.util.List;

/**
 * Created by Thanh Q. Le
 * Date: 20/06/16
 * Time: 9:15 AM
 */
public class CucumberReports {
    private String buildNumber;
    private List<CucumberReport> cucumberReport;

    public String getBuildNumber() {
        return buildNumber;
    }

    public void setBuildNumber(String buildNumber) {
        this.buildNumber = buildNumber;
    }

    public List<CucumberReport> getCucumberReport() {
        return cucumberReport;
    }

    public void setCucumberReport(List<CucumberReport> cucumberReport) {
        this.cucumberReport = cucumberReport;
    }
}
