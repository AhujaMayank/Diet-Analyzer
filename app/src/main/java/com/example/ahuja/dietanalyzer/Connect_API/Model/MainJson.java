package com.example.ahuja.dietanalyzer.Connect_API.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MainJson {

    @SerializedName("text")
    private String text;
    @SerializedName("parsed")
    List<ParsedJson> parsed;
    @SerializedName("hints")
    List<HintsJson> hints;
    @SerializedName("page")
    private long page;
    @SerializedName("numPages")
    private long numPages;
    public void setText(String text) {
        this.text = text;
    }
    public void setParsed(List<ParsedJson> parsed) {
        this.parsed = parsed;
    }
    public void setHints(List<HintsJson> hints) {
        this.hints = hints;
    }
    public void setPage(long page) {
        this.page = page;
    }
    public void setNumPages(long numPages) {
        this.numPages = numPages;
    }

    public String getText() {
        return text;
    }
    public List<ParsedJson> getParsed() {
        return parsed;
    }
    public List<HintsJson> getHints() {
        return hints;
    }
    public long getPage() {
        return page;
    }
    public long getNumPages() {
        return numPages;
    }




}
