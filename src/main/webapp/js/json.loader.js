/**
 * Created by Thanh Q. Le on 21/06/16.
 */

var threshold;

function renderCharts(result) {
    var xmlhttp = new XMLHttpRequest();
    var url = '.';
    var buildStatistics = [];
    var scenarios = [];
    var stableScenarios = [];
    var unstableScenario = [];
    loadBasicPage(result);
    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
            var temp = result;
            threshold = result["threshold"];
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
        if (isNumeric(build)) {
            var buildData = buildReport[build];
            labels.push(buildData["buildNumber"]);
            var display;
            if (type == "duration") {
                data.push((buildData["buildTime"]/60).toFixed(3));
                display = "Test Duration (in minutes)";
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
            tension:0,
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
    createTableHeader(table);
    for (var scenario in data) {
        if (data[scenario]["failRate"] < threshold) {
            var lastRow = table.rows.length;
            var row = table.insertRow(lastRow);
            var scenarioName = row.insertCell(0);
            var runTimes = row.insertCell(1);
            var failedRate = row.insertCell(2);
            scenarioName.innerHTML = data[scenario]["scenarioName"];
            runTimes.innerHTML = data[scenario]["executedTime"];
            failedRate.innerHTML = parseFloat(data[scenario]["failRate"] * 100).toFixed(2) + "%";
        }
    }
}

function createTableHeader(table) {
    var header = table.insertRow(0);
    var scenarioNameHeader = header.insertCell(0);
    var executedTimeHeader = header.insertCell(1);
    var failRateHeader = header.insertCell(2);
    scenarioNameHeader.innerHTML = "Scenario Name";
    executedTimeHeader.innerHTML = "Executed Times";
    failRateHeader.innerHTML = "Failed Rate";
}

function createUnStableScenarioOutput(data) {
    var table = document.getElementById("unstable-scenario").getElementsByTagName("tbody")[0];
    createTableHeader(table);
    for (var scenario in data) {
        if (data[scenario]["failRate"] >= threshold) {
            var lastRow = table.rows.length;
            var row = table.insertRow(lastRow);
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

function loadBasicPage(data) {
    if (data == null) {
        messageForNoData();
    } else {
        createLayout();
    }
}

function createLayout() {
    var time = document.createElement("h3");
    time.innerHTML = "Creation time";
    var timeSpan = document.createElement("span");
    timeSpan.id = "time-created";
    time.appendChild(timeSpan);
    document.getElementById("result").appendChild(time);
    createTrendResultLayout();
    createTrendDurationLayout();
    createTrendScenarioLayout();
    createTable("Top Stable Scenario", "stable-scenario", "stable-scenario");
    createTable("Top Unstable Scenario", "unstable-scenario", "unstable-scenario");

}

function createTrendResultLayout() {
    var title = document.createElement("h2");
    title.innerHTML = "Test Result Trend";
    var trendResult = document.createElement("div");
    var trendResultCanvas = document.createElement("canvas");
    trendResultCanvas.id = "results-trend";
    trendResultCanvas.style = "width: 80%";
    trendResultCanvas.className = "chart";
    trendResult.appendChild(trendResultCanvas);
    addToPage(title);
    addToPage(trendResult);
}

function createTrendDurationLayout() {
    var title = document.createElement("h2");
    title.innerHTML = "Test Duration Trend";
    var trendResult = document.createElement("div");
    var trendResultCanvas = document.createElement("canvas");
    trendResultCanvas.id = "duration-trend";
    trendResultCanvas.style = "width: 80%";
    trendResult.appendChild(trendResultCanvas);
    addToPage(title);
    addToPage(trendResult);
}

function createTrendScenarioLayout() {
    var title = document.createElement("h2");
    title.innerHTML = "Test Scenario Trend";
    var trendResult = document.createElement("div");
    var trendResultCanvas = document.createElement("canvas");
    trendResultCanvas.id = "scenario-trend";
    trendResultCanvas.style = "width: 80%";
    trendResult.appendChild(trendResultCanvas);
    addToPage(title);
    addToPage(trendResult);
}

function createTable(titleText, id, clazz) {
    var div = document.createElement("div");
    var title = document.createElement("h2");
    title.innerHTML = titleText;
    div.appendChild(title);

    var table = document.createElement("table");
    table.id = id;
    table.className = clazz;
    var theader = document.createElement("theader");
    var tbody = document.createElement("tbody");

    table.appendChild(theader);
    table.appendChild(tbody);
    div.appendChild(table);
    addToPage(div);
}

function messageForNoData() {
    var textarea = document.createTextNode("No data available!");
    addToPage(textarea);
}

function addToPage(param) {
    document.getElementById("result").appendChild(param);
}