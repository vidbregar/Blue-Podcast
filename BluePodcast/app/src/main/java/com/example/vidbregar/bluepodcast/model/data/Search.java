package com.example.vidbregar.bluepodcast.model.data;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Search {

    @SerializedName("count")
    private int count;
    @SerializedName("total")
    private int total;
    @SerializedName("next_offset")
    private int nextOffset;
    @SerializedName("results")
    private List<SearchResult> results = null;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getNextOffset() {
        return nextOffset;
    }

    public void setNextOffset(int nextOffset) {
        this.nextOffset = nextOffset;
    }

    public List<SearchResult> getResults() {
        return results;
    }

    public void setResults(List<SearchResult> results) {
        this.results = results;
    }
}
