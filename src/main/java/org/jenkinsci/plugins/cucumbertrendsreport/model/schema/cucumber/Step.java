package org.jenkinsci.plugins.cucumbertrendsreport.model.schema.cucumber;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

@Generated("org.jsonschema2pojo")
public class Step {

    @SerializedName("keyword")
    @Expose
    private String keyword;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("line")
    @Expose
    private Integer line;
    @SerializedName("match")
    @Expose
    private Match match;
    @SerializedName("result")
    @Expose
    private Result result;
    @SerializedName("rows")
    @Expose
    private List<Row> rows = new ArrayList<Row>();

    /**
     * 
     * @return
     *     The keyword
     */
    public String getKeyword() {
        return keyword;
    }

    /**
     * 
     * @param keyword
     *     The keyword
     */
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

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

    /**
     * 
     * @return
     *     The match
     */
    public Match getMatch() {
        return match;
    }

    /**
     * 
     * @param match
     *     The match
     */
    public void setMatch(Match match) {
        this.match = match;
    }

    /**
     * 
     * @return
     *     The result
     */
    public Result getResult() {
        return result;
    }

    /**
     * 
     * @param result
     *     The result
     */
    public void setResult(Result result) {
        this.result = result;
    }

    /**
     * 
     * @return
     *     The rows
     */
    public List<Row> getRows() {
        return rows;
    }

    /**
     * 
     * @param rows
     *     The rows
     */
    public void setRows(List<Row> rows) {
        this.rows = rows;
    }

}
