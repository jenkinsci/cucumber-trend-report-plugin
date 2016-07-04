package org.jenkinsci.plugins.cucumbertrendsreport.utils;

import org.apache.log4j.Logger;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Thanh.Q.Le on 15/05/2015.
 */
public class Common {
    private final static Logger logger = Logger.getLogger(Common.class);

    public static List<String> getSubFolder(String path) {
        List<String> res = new ArrayList<>();
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        if (listOfFiles != null) {
            for (File listOfFile : listOfFiles) {
                if (listOfFile.isDirectory()) {

                    res.add(listOfFile.getAbsolutePath());
                }
            }
        } else {
            logger.error("Could not get any name of sub-folder under folder '" + path + "'");
        }
        return res;
    }

    public static void createTextFile(File file, String contents) {
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file), "UTF-8"));
            bufferedWriter.write(contents);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static String getCurrentDateTime(String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date now = new Date();
        return simpleDateFormat.format(now);
    }

    public static String getText(String filename) {
        String content = "";
        try {
            File fileDir = new File(filename);
            FileInputStream fileInputStream = new FileInputStream(fileDir);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream,"UTF8");
            BufferedReader in = new BufferedReader(inputStreamReader);
            String str;

            StringBuilder buffer = new StringBuilder();
            while ((str = in.readLine()) != null) {
                buffer.append(str);
            }
            content = buffer.toString();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

}