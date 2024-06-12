package com.example.corruptionperceptionindex.src.model;

public class CityData {
    private int id;
    private String name;
    private double indexResult;

    public CityData(int id, String name, double indexResult) {
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

    public double getIndexResult() {
        return indexResult;
    }
}
