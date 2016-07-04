package org.jenkinsci.plugins.cucumbertrendsreport;

import hudson.model.Run;
import org.jenkinsci.plugins.cucumbertrendsreport.utils.Common;
import java.io.File;

/**
 * Created by Thanh Q. Le
 * Date: 25/06/16
 * Time: 4:24 PM
 */
public class CucumberTrendReportBuildAction extends CucumberTrendReportBaseAction{
    private final Run<?, ?> build;

    public CucumberTrendReportBuildAction(Run<?, ?> build) {
        this.build = build;
    }

    @Override
    protected String getTitle() {
        return this.build.getDisplayName();
    }

    @Override
    protected File dir() {
        return new File(build.getRootDir(), BASE_URL);
    }

    public String getBuildURL(){
        return this.build.getUrl();
    }

    public String getJsonBuildResult(){
        File resultFile = new File(dir(), "result.json");
        return Common.getText(resultFile.toString());
    }
}
