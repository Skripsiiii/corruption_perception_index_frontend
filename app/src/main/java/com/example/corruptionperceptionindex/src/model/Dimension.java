package com.example.corruptionperceptionindex.src.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Dimension {
    private int id;
    private String dimensionNumber;
    private int questionnaireId;
    private String name;
    private List<Indicator> indicators;

    public Dimension(JSONObject jsonObject) throws JSONException {
        this.id = jsonObject.optInt("id", 0);
        this.dimensionNumber = jsonObject.optString("dimension_number", "");
        this.questionnaireId = jsonObject.optInt("questionnaire_id", 0);
        this.name = jsonObject.optString("name", "");
        this.indicators = new ArrayList<>();

        JSONArray indicatorsArray = jsonObject.optJSONArray("indicators");
        if (indicatorsArray != null) {
            for (int i = 0; i < indicatorsArray.length(); i++) {
                indicators.add(new Indicator(indicatorsArray.getJSONObject(i)));
            }
        }
    }

    public int getId() {
        return id;
    }

    public String getDimensionNumber() {
        return dimensionNumber;
    }

    public int getQuestionnaireId() {
        return questionnaireId;
    }

    public String getName() {
        return name;
    }

    public List<Indicator> getIndicators() {
        return indicators;
    }

    public List<Question> getQuestions() {
        List<Question> questions = new ArrayList<>();
        for (Indicator indicator : indicators) {
            questions.addAll(indicator.getQuestions());
        }
        return questions;
    }
}
