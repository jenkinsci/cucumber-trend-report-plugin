package org.jenkinsci.plugins.cucumbertrendsreport.reports;


import org.jenkinsci.plugins.cucumbertrendsreport.model.schema.cucumber.*;
import org.jenkinsci.plugins.cucumbertrendsreport.model.schema.trends.*;
import org.jenkinsci.plugins.cucumbertrendsreport.utils.Common;
import org.jenkinsci.plugins.cucumbertrendsreport.utils.CustomCompareByFailure;
import org.jenkinsci.plugins.cucumbertrendsreport.utils.CustomCompareByPass;
import org.jenkinsci.plugins.cucumbertrendsreport.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Thanh Q. Le
 * Date: 20/06/16
 * Time: 9:07 AM
 */
public class TrendReportGeneration {

    public void createJsonResults(String output, List<CucumberReports> allReports, Double threshold) {
        Utils utils = new Utils();
        BuildReports jsonResult = generateTrendReport(allReports);
        jsonResult.setThreshold(threshold);
        utils.createJsonFile(jsonResult, output);
    }

    public BuildReports generateTrendReport(List<CucumberReports> allReports) {
        BuildReports buildReports = new BuildReports();
        List<BuildReport> builds;
        List<Scenario> scenarios;
        List<StableScenarios> stableScenarios;
        List<UnstableScenarios> unstableScenarios;

        builds = getBuildStatistics(allReports);
        scenarios = getScenariosStatistics(allReports);
        stableScenarios = getStableScenarios(scenarios);
        unstableScenarios = getUnstableScenario(scenarios);
        buildReports.setBuildReports(builds);
        buildReports.setScenarios(scenarios);
        buildReports.setStableScenarios(stableScenarios);
        buildReports.setUnstableScenarios(unstableScenarios);
        buildReports.setReportTime(Common.getCurrentDateTime("HH:mm:ss dd/MM/YYYY"));
        return buildReports;
    }

    private List<BuildReport> getBuildStatistics(List<CucumberReports> allReports) {
        List<BuildReport> builds = new ArrayList<>();

        for (CucumberReports cucumberReports : allReports) {
            BuildReport buildReport = new BuildReport();
            List<Element> scenarioForBuild = getScenarioForBuild(cucumberReports.getBuildNumber(), allReports);

            buildReport.setBuildNumber(Integer.valueOf(cucumberReports.getBuildNumber()));
            buildReport.setNumberOfScenarios(scenarioForBuild.size());
            buildReport.setBuildTime(String.valueOf(calculateBuildTime(scenarioForBuild)));
            if (scenarioForBuild.size() == 0) {
                buildReport.setFailRate(0.0);
            } else {
                buildReport.setFailRate(calculateFailedScenario(scenarioForBuild) / scenarioForBuild.size());
            }
            builds.add(buildReport);
        }
        return builds;
    }

    private List<Scenario> getScenariosStatistics(List<CucumberReports> allReports) {
        List<Scenario> scenarios = new ArrayList<>();
        List<String> existingScenario = new ArrayList<>();
        List<Element> allElements = new ArrayList<>();
        for (CucumberReports reports : allReports) {
            String buildNumber = reports.getBuildNumber();
            List<Element> scenariosForBuild = getScenarioForBuild(buildNumber, allReports);
            for (Element element : scenariosForBuild) {
                if (!existingScenario.contains(element.getName())) {
                    Scenario scenario = new Scenario();
                    scenario.setScenarioName(element.getName());

                    scenarios.add(scenario);
                    existingScenario.add(element.getName());
                }
            }
            allElements.addAll(scenariosForBuild);
        }

        for (Scenario scenario : scenarios) {
            Integer failed = 0;
            Integer passed = 0;
            for (Element element : allElements) {
                if (scenario.getScenarioName().equalsIgnoreCase(element.getName())) {
                    if (isScenarioFailed(element)) {
                        failed += 1;
                    } else {
                        passed += 1;
                    }
                }
            }
            scenario.setPassed(passed);
            scenario.setFailed(failed);
        }

        return scenarios;
    }

    private List<StableScenarios> getStableScenarios(List<Scenario> scenarios) {
        List<StableScenarios> stableScenarios = new ArrayList<>();
        Collections.sort(scenarios, new CustomCompareByFailure());
        for (Scenario scenario : scenarios) {
            StableScenarios stableScenario = new StableScenarios();
            stableScenario.setScenarioName(scenario.getScenarioName());
            Integer passed = scenario.getPassed();
            Integer failed = scenario.getFailed();
            Integer total = passed + failed;
            Double failRate = (double) failed / total;
            stableScenario.setExecutedTime(total);
            stableScenario.setFailRate(failRate);
            stableScenarios.add(stableScenario);
        }
        return stableScenarios;
    }

    private List<UnstableScenarios> getUnstableScenario(List<Scenario> scenarios) {
        List<UnstableScenarios> unstableScenarios = new ArrayList<>();
        Collections.sort(scenarios, new CustomCompareByPass());
        for (Scenario scenario : scenarios) {
            UnstableScenarios unstableScenario = new UnstableScenarios();
            unstableScenario.setScenarioName(scenario.getScenarioName());
            Integer passed = scenario.getPassed();
            Integer failed = scenario.getFailed();
            Integer total = passed + failed;
            Double failRate = (double) failed / total;
            unstableScenario.setFailRate(failRate);
            unstableScenario.setExecutedTime(total);
            unstableScenarios.add(unstableScenario);
        }
        return unstableScenarios;
    }


    private Boolean isScenarioFailed(Element element) {
        List<Step> steps = element.getSteps();
        for (Step step : steps) {
            if (step.getResult().getStatus().equalsIgnoreCase("failed")||step.getResult().getStatus().equalsIgnoreCase("skipped")) return true;
        }
        return false;
    }

    private List<Element> getScenarioForBuild(String buildNumber, List<CucumberReports> allReports) {
        List<Element> scenarios = new ArrayList<>();
        CucumberReports cucumberReports = new CucumberReports();
        for (CucumberReports reports : allReports) {
            if (reports.getBuildNumber().equalsIgnoreCase(buildNumber)) {
                cucumberReports = reports;
            }
        }
        for (CucumberReport cucumberReport : cucumberReports.getCucumberReport()) {
            List<Element> elements = cucumberReport.getElements();
            for (Element element : elements) {
                if (element.getKeyword().equalsIgnoreCase("Scenario")) {
                    scenarios.add(element);
                }
            }
        }
        return scenarios;
    }

    private Double calculateBuildTime(List<Element> scenarios) {
        Double time = 0.0;
        for (Element scenario : scenarios) {
            List<Step> steps = scenario.getSteps();
            for (Step step : steps) {
                Result result = step.getResult();
                time = time + Double.valueOf(result.getDuration());
            }
        }
        return time / 1000000000;
    }

    private Double calculateFailedScenario(List<Element> scenarios) {
        Double fail = 0.0;
        for (Element scenario : scenarios) {
            if (isScenarioFailed(scenario)) fail += 1;
        }
        return fail;
    }
}
