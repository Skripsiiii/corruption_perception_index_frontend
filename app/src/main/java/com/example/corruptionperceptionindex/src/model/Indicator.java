package com.example.corruptionperceptionindex.src.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Indicator {
    private int id;
    private String indicatorNumber;
    private int dimensionId;
    private String name;
    private List<Question> questions;

    public Indicator(JSONObject jsonObject) throws JSONException {
        this.id = jsonObject.optInt("id", 0);
        this.indicatorNumber = jsonObject.optString("indicator_number", "");
        this.dimensionId = jsonObject.optInt("dimension_id", 0);
        this.name = jsonObject.optString("name", "");
        this.questions = new ArrayList<>();

        JSONArray questionsArray = jsonObject.optJSONArray("questions");
        if (questionsArray != null) {
            for (int i = 0; i < questionsArray.length(); i++) {
                questions.add(new Question(questionsArray.getJSONObject(i)));
            }
        }
    }

    public int getId() {
        return id;
    }

    public String getIndicatorNumber() {
        return indicatorNumber;
    }

    public int getDimensionId() {
        return dimensionId;
    }

    public String getName() {
        return name;
    }

    public List<Question> getQuestions() {
        return questions;
    }
}
