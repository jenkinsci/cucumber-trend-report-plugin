package org.jenkinsci.plugins.cucumbertrendsreport.model.schema.trends;

import java.util.List;

/**
 * Created by Thanh Q. Le
 * Date: 20/06/16
 * Time: 8:58 AM
 */
public class BuildReports {
    private String reportName;
    private String reportTime;
    private List<BuildReport> buildReports;
    private List<Scenario> scenarios;
    private List<UnstableScenarios> unstableScenarios;
    private List<StableScenarios> stableScenarios;

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getReportTime() {
        return reportTime;
    }

    public void setReportTime(String reportTime) {
        this.reportTime = reportTime;
    }

    public List<BuildReport> getBuildReports() {
        return buildReports;
    }

    public void setBuildReports(List<BuildReport> buildReports) {
        this.buildReports = buildReports;
    }

    public List<Scenario> getScenarios() {
        return scenarios;
    }

    public void setScenarios(List<Scenario> scenarios) {
        this.scenarios = scenarios;
    }

    public List<UnstableScenarios> getUnstableScenarios() {
        return unstableScenarios;
    }

    public void setUnstableScenarios(List<UnstableScenarios> unstableScenarios) {
        this.unstableScenarios = unstableScenarios;
    }

    public List<StableScenarios> getStableScenarios() {
        return stableScenarios;
    }

    public void setStableScenarios(List<StableScenarios> stableScenarios) {
        this.stableScenarios = stableScenarios;
    }
}
