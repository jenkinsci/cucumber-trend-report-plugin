package org.jenkinsci.plugins.cucumbertrendsreport.utils;

import org.jenkinsci.plugins.cucumbertrendsreport.model.schema.cucumber.CucumberReport;
import org.jenkinsci.plugins.cucumbertrendsreport.model.schema.cucumber.Element;
import org.junit.Before;
import org.junit.Test;
import java.util.List;

import static org.junit.Assert.*;

public class JsonHelperTest {
    private String path;
    private JsonHelper jsonHelper;

    @Before
    public void setUp() throws Exception {
        this.path = JsonHelper.class.getClass().getResource("/json/test.json").getPath();
        this.jsonHelper = new JsonHelper();
    }

    @Test
    public void testLoadJsonReportFile() throws Exception {
        List<CucumberReport> cucumberReports = this.jsonHelper.loadJsonReportFile(this.path, CucumberReport[].class);
        assertEquals(cucumberReports.get(0).getKeyword(),"Feature");
        assertEquals(cucumberReports.get(0).getName(),"Demonstration session #2");
        assertEquals(cucumberReports.get(0).getId(),"demonstration-session-#2");
        Element element = cucumberReports.get(0).getElements().get(0);
        assertEquals(element.getKeyword(),"Scenario");
        assertEquals(element.getName(),"Login to the travel business partner page");
    }
}