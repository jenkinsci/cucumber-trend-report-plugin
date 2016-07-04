package org.jenkinsci.plugins.cucumbertrendsreport.reports;

import org.apache.commons.io.FileUtils;
import org.jenkinsci.plugins.cucumbertrendsreport.model.schema.cucumber.CucumberReport;
import org.jenkinsci.plugins.cucumbertrendsreport.model.schema.cucumber.CucumberReports;
import org.jenkinsci.plugins.cucumbertrendsreport.utils.JsonHelper;
import org.jenkinsci.plugins.cucumbertrendsreport.utils.Utils;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thanh Q. Le
 * Date: 5/24/16
 * Time: 2:40 PM
 */
public class ReportCollection {
    private String reportFolder;
    private List<String> builds;
    private Integer numberOfBuild;
    private String reportName;
    private List<CucumberReports> reports;


    public void initiate(String buildPath, String reportFolder, String reportName) {
        Utils utils = new Utils();
        this.reportFolder = reportFolder;
        this.builds = utils.getBuilds(buildPath);
        this.numberOfBuild = this.builds.size();
        this.reportName = reportName;
        this.reports = readJsonReports();
    }

    private List<CucumberReports> readJsonReports() {
        JsonHelper jsonHelper = new JsonHelper();
        List<CucumberReports> reports = new ArrayList<>();
        if (this.numberOfBuild < 1) return null;
        for (String buildPath : this.builds) {
            CucumberReports cucumberReports = new CucumberReports();
            File jsonReportFile = FileUtils.getFile(String.valueOf(buildPath), this.reportFolder);
            String buildNumber = FileUtils.getFile(buildPath).getName();
            if (buildNumber.matches("\\d+")) {
                jsonReportFile = FileUtils.getFile(jsonReportFile, this.reportName);
                List<CucumberReport> cucumberReport = jsonHelper.loadJsonReportFile(jsonReportFile.toString(), CucumberReport[].class);
                cucumberReports.setCucumberReport(cucumberReport);
                cucumberReports.setBuildNumber(buildNumber);
                reports.add(cucumberReports);
            }
        }
        return reports;
    }

    public List<CucumberReports> getReports() {
        return reports;
    }

}
