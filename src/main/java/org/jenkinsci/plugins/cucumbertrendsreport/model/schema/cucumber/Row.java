package org.jenkinsci.plugins.cucumbertrendsreport.model.schema.cucumber;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

@Generated("org.jsonschema2pojo")
public class Row {

    @SerializedName("cells")
    @Expose
    private List<String> cells = new ArrayList<String>();
    @SerializedName("line")
    @Expose
    private Integer line;

    /**
     * 
     * @return
     *     The cells
     */
    public List<String> getCells() {
        return cells;
    }

    /**
     * 
     * @param cells
     *     The cells
     */
    public void setCells(List<String> cells) {
        this.cells = cells;
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
