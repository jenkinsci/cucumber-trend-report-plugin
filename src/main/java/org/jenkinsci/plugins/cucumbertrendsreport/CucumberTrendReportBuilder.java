package org.jenkinsci.plugins.cucumbertrendsreport;

import hudson.Launcher;
import hudson.Extension;
import hudson.FilePath;
import hudson.model.*;
import hudson.util.FormValidation;
import hudson.tasks.Builder;
import hudson.tasks.BuildStepDescriptor;
import jenkins.tasks.SimpleBuildStep;
import org.apache.commons.io.FileUtils;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;
import org.jenkinsci.plugins.cucumbertrendsreport.reports.ReportCollection;
import org.jenkinsci.plugins.cucumbertrendsreport.reports.TrendReportGeneration;

import javax.servlet.ServletException;
import java.io.File;
import java.io.IOException;

public class CucumberTrendReportBuilder extends Builder implements SimpleBuildStep {

    private final String unstableThreshold;
    private final String reportFolder;
    private final String jsonReportName;

    @DataBoundConstructor
    public CucumberTrendReportBuilder(String unstableThreshold, String reportFolder,String jsonReportName) {
        this.unstableThreshold = unstableThreshold;
        this.reportFolder = reportFolder;
        this.jsonReportName = jsonReportName;
    }

    public String getUnstableThreshold() {
        return unstableThreshold;
    }

    public String getReportFolder() {
        return reportFolder;
    }

    public String getJsonReportName(){
        return jsonReportName;
    }

    @Override
    public void perform(Run<?, ?> build, FilePath workspace, Launcher launcher, TaskListener listener) throws IOException {
        // This is where you 'build' the project.
        String workplace = String.valueOf(workspace);
        File jobDirectory = new File(workplace).getParentFile();
        listener.getLogger().println(jobDirectory);

        File targetBuildDirectory = new File(build.getRootDir(), CucumberTrendReportBaseAction.BASE_URL);
        if (!targetBuildDirectory.exists()) {
            if(!targetBuildDirectory.mkdirs()) return;
        }
        File targetFile = new File(targetBuildDirectory, "result.json");

        File buildPath = new File(jobDirectory, "builds");

        File reportFolderInWorkspace =  new File(workplace,reportFolder);

        FileUtils.copyFile(new File(reportFolderInWorkspace,jsonReportName), new File(targetBuildDirectory,jsonReportName));
        ReportCollection reportCollection = new ReportCollection();
        reportCollection.initiate(buildPath.toString(), targetBuildDirectory.getName(), jsonReportName);
        TrendReportGeneration trendReportGeneration = new TrendReportGeneration();
        trendReportGeneration.createJsonResults(targetFile.toString(), reportCollection.getReports());

        build.addAction(new CucumberTrendReportBuildAction(build));

    }

    @Override
    public Action getProjectAction(AbstractProject<?, ?> project) {
        return new CucumberTrendReportProjectAction(project);
    }

    @Override
    public DescriptorImpl getDescriptor() {
        return (DescriptorImpl) super.getDescriptor();
    }

    @Extension
    public static final class DescriptorImpl extends BuildStepDescriptor<Builder> {
        public DescriptorImpl() {
            load();
        }

        public FormValidation doCheckReportFolder(@QueryParameter String value)
                throws IOException, ServletException {
            if (value.length() == 0)
                return FormValidation.error("You have to configure report folder. That is where you json report is generated under workplace");
            return FormValidation.ok();
        }

        public FormValidation doJsonReportName(@QueryParameter String value)
                throws IOException, ServletException {
            if (value.length() == 0)
                return FormValidation.error("You have to configure json report name. Ex: cucumber.json");
            return FormValidation.ok();
        }

        public FormValidation doCheckUnstableThreshold(@QueryParameter String value)
                throws IOException, ServletException {
            if (value.length() == 0)
                return FormValidation.warning("Default value is 40%");

            if (value.contains("%")) {
                value = value.replace("%", "");
            }

            if (!value.matches("\\d+")) return FormValidation.error("Threshold must be digits");

            if (Double.valueOf(value) < 0.0 || Double.valueOf(value) > 100) {
                return FormValidation.error("The threshold must be between 0 and 100%");
            }
            return FormValidation.ok();
        }

        public boolean isApplicable(Class<? extends AbstractProject> aClass) {
            return true;
        }

        public String getDisplayName() {
            return "Cucumber Trend Report";
        }
    }
}

