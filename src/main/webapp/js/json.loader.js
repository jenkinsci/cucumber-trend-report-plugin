/**
 * Created by Thanh Q. Le on 21/06/16.
 */

function renderCharts(result) {
    var xmlhttp = new XMLHttpRequest();
    var url = '.';
    var buildStatistics = [];
    var scenarios = [];
    var stableScenarios = [];
    var unstableScenario = [];

    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
            var temp = result;
            document.getElementById("time-created").innerHTML = temp["reportTime"];
            buildStatistics = temp["buildReports"];
            scenarios = temp["scenarios"];
            stableScenarios = temp["stableScenarios"];
            unstableScenario = temp["unstableScenarios"];
            buildStatistics.sort(function (a, b) {
                return a.buildNumber - b.buildNumber;
            });

            drawResultChart(buildStatistics);
            drawDurationChart(buildStatistics);
            drawScenarioChart(buildStatistics);

            createStableScenarioOutput(stableScenarios);
            createUnStableScenarioOutput(unstableScenario);
        }
    };
    xmlhttp.open("GET", url, true);
    xmlhttp.send();
}

function setConfig(arr, type, color) {
    var labels = [];
    var data = [];
    var buildReport = arr;

    for (var build in buildReport) {
        if(isNumeric(build)){
            var buildData = buildReport[build];
            labels.push(buildData["buildNumber"]);
            var display;
            if (type == "duration") {
                data.push(buildData["buildTime"]);
                display = "Test Duration (in second)";
            }
            if (type == "result") {
                data.push(buildData["failRate"] * 100);
                display = "Test Failed rate (%)"
            }
            if (type == "scenarios") {
                data.push(buildData["numberOfScenarios"]);
                display = "Number of test scenarios"
            }
        }
    }
    return {
        type: "line",
        data: {
            labels: labels,
            datasets: [
                {
                    label: display,
                    backgroundColor: color,
                    data: data
                }
            ]
        },
        options: {
            responsive: true,
            scales: {
                xAxes: [
                    {
                        display: true,
                        scaleLabel: {
                            show: true,
                            labelString: 'Build'
                        }
                    }
                ],
                yAxes: [
                    {
                        ticks: {
                            userCallback: function (value, index, values) {
                                return value;
                            }
                        }
                    }
                ]
            }
        }

    };
}

function drawDurationChart(jsonData) {
    var duration = document.getElementById('duration-trend').getContext('2d');
    var config = setConfig(jsonData, "duration", "blue");
    window.durationChart = new Chart(duration, config);
}

function drawResultChart(jsonData) {
    var result = document.getElementById('results-trend').getContext('2d');
    var config = setConfig(jsonData, "result", "red");
    new Chart(result, config);
}

function drawScenarioChart(jsonData) {
    var result = document.getElementById('scenario-trend').getContext('2d');
    var config = setConfig(jsonData, "scenarios", "green");
    new Chart(result, config);
}

function createStableScenarioOutput(data) {
    var table = document.getElementById("stable-scenario").getElementsByTagName("tbody")[0];
    for (var scenario in data) {
        if (scenario < 5) {
            var row = table.insertRow(scenario);
            var scenarioName = row.insertCell(0);
            var runTimes = row.insertCell(1);
            var failedRate = row.insertCell(2);
            scenarioName.innerHTML = data[scenario]["scenarioName"];
            runTimes.innerHTML = data[scenario]["executedTime"];
            failedRate.innerHTML = parseFloat(data[scenario]["failRate"] * 100).toFixed(2) + "%";
        }

    }
}
function createUnStableScenarioOutput(data) {
    var table = document.getElementById("unstable-scenario").getElementsByTagName("tbody")[0];
    for (var scenario in data) {
        if (scenario < 5 && data[scenario]["failRate"] > 0) {
            var row = table.insertRow(scenario);
            var scenarioName = row.insertCell(0);
            var runTimes = row.insertCell(1);
            var failedRate = row.insertCell(2);
            scenarioName.innerHTML = data[scenario]["scenarioName"];
            runTimes.innerHTML = data[scenario]["executedTime"];
            failedRate.innerHTML = parseFloat(data[scenario]["failRate"] * 100).toFixed(2) + "%";
        }
    }
}
function isNumeric(n) {
    return !isNaN(parseFloat(n)) && isFinite(n);
}