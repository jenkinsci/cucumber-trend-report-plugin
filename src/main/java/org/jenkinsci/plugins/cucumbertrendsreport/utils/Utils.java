package org.jenkinsci.plugins.cucumbertrendsreport.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jenkinsci.plugins.cucumbertrendsreport.model.schema.trends.BuildReports;
import java.io.File;
import java.util.List;

/**
 * Created by Thanh Q. Le
 * Date: 5/24/16
 * Time: 2:55 PM
 */
public class Utils {

    public List<String> getBuilds(String path){
        return Common.getSubFolder(path);
    }

    public void createJsonFile(BuildReports obj, String filename){
        Gson gson = new GsonBuilder()
                .enableComplexMapKeySerialization()
                .serializeNulls()
                .setPrettyPrinting()
                .setVersion(1.0)
                .serializeSpecialFloatingPointValues()
                .create();

        String jsonInString = gson.toJson(obj);
        Common.createTextFile(new File(filename),jsonInString);
    }
}
