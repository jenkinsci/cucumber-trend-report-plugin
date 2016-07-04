package org.jenkinsci.plugins.cucumbertrendsreport;

import hudson.model.Action;
import hudson.util.HttpResponses;
import org.kohsuke.stapler.HttpResponse;
import org.kohsuke.stapler.Stapler;
import org.kohsuke.stapler.interceptor.RequirePOST;

import java.io.*;

/**
 * Created by Thanh Q. Le
 * Date: 25/06/16
 * Time: 3:22 PM
 */
public abstract class CucumberTrendReportBaseAction implements Action {
    protected static final String BASE_URL = "cucumber-trend-reports";

    private static final String DEFAULT_PAGE = "home";

    public String getUrlName() {
        return BASE_URL;
    }

    public String getDisplayName() {
        return "Cucumber Trend Reports";
    }

    public String getIconFileName() {
        return "/plugin/cucumber-trends-report/icon.png";
    }

    @RequirePOST
    public HttpResponse doTest(Stapler req){
        System.out.print("doTest");
        return HttpResponses.redirectTo(".");
    }

    protected abstract String getTitle();

    protected abstract File dir();



}
