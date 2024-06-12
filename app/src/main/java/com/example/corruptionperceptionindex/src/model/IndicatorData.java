package com.example.corruptionperceptionindex.src.model;

public class IndicatorData {
    private int id;
    private String name;
    private int indexResult;

    public IndicatorData(int id, String name, int indexResult) {
        this.id = id;
        this.name = name;
        this.indexResult = indexResult;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getIndexResult() {
        return indexResult;
    }
}
