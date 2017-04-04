package com.timmy.framework.netFw.model;

import java.util.List;

/**
 * Created by timmy1 on 17/4/4.
 */

public class GankResult {

    private boolean error;
    private List<GankObj> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<GankObj> getResults() {
        return results;
    }

    public void setResults(List<GankObj> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "GankResult{" +
                "error=" + error +
                ", results=" + results +
                '}';
    }
}
