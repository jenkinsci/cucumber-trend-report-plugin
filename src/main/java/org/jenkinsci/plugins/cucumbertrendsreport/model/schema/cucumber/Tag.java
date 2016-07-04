package org.jenkinsci.plugins.cucumbertrendsreport.model.schema.cucumber;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Tag {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("line")
    @Expose
    private Integer line;

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     The line
     */
    public Integer getLine() {
        return line;
    }

    /**
     * 
     * @param line
     *     The line
     */
    public void setLine(Integer line) {
        this.line = line;
    }

}
