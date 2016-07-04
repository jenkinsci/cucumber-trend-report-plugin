package org.jenkinsci.plugins.cucumbertrendsreport;

import hudson.model.*;
import org.jenkinsci.plugins.cucumbertrendsreport.utils.Common;
import java.io.File;

/**
 * Created by Thanh Q. Le
 * Date: 25/06/16
 * Time: 12:50 PM
 */

public class CucumberTrendReportProjectAction extends CucumberTrendReportBaseAction implements ProminentProjectAction {
    private final AbstractProject<?, ?> project;

    public CucumberTrendReportProjectAction(AbstractProject<?, ?> project) {
        this.project = project;
    }

    @Override
    protected File dir() {
        Run<?, ?> run = this.project.getLastCompletedBuild();
        if (run != null) {
            File archiveDir = getBuildArchiveDir(run);

            if (archiveDir.exists()) {
                return archiveDir;
            }
        }

        return getProjectArchiveDir();
    }

    private File getProjectArchiveDir() {
        return new File(project.getRootDir(), CucumberTrendReportBaseAction.BASE_URL);
    }

    private File getBuildArchiveDir(Run<?, ?> run) {
        return new File(run.getRootDir(), CucumberTrendReportBaseAction.BASE_URL);
    }

    @Override
    protected String getTitle() {
        return this.project.getDisplayName();
    }

    public String getResultFile() {
        return dir().toString();
    }

    public String getJsonResult() {
        File resultFile = new File(getResultFile(), "result.json");
        return Common.getText(resultFile.toString());

    }
}
