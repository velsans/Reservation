package com.reservation.model;

import java.util.ArrayList;

public class AlbumResponceModel {
    int resultCount;
    ArrayList<AlbumModel> results=new ArrayList<>();

    public int getResultCount() {
        return resultCount;
    }

    public void setResultCount(int resultCount) {
        this.resultCount = resultCount;
    }

    public ArrayList<AlbumModel> getResults() {
        return results;
    }

    public void setResults(ArrayList<AlbumModel> results) {
        this.results = results;
    }
}
