package org.jenkinsci.plugins.cucumbertrendsreport.utils;

import org.jenkinsci.plugins.cucumbertrendsreport.model.schema.trends.Scenario;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by Thanh Q. Le
 * Date: 23/06/16
 * Time: 4:05 PM
 */
public class CustomCompareByPass implements Comparator<Scenario>,Serializable {
    @Override
    public int compare(Scenario scenario1, Scenario scenario2) {
        return scenario1.getPassed().compareTo(scenario2.getPassed());
    }
}
