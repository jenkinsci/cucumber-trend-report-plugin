package org.jenkinsci.plugins.cucumbertrendsreport.utils;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Thanh Q. Le
 * Date: 5/23/16
 * Time: 3:44 PM
 */
public class JsonHelper {
    public <T> List<T> loadJsonReportFile(String filename, Class<T[]> clazz) {
        Gson gson = new Gson();
        T[] report;
        String content = Common.getText(filename);
        report = gson.fromJson(content, clazz);
        if (report == null) {
            return Collections.emptyList();

        } else {
            return Arrays.asList(report);
        }
    }
}
