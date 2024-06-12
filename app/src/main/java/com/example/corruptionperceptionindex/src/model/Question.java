package com.example.corruptionperceptionindex.src.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Question {
    private int id;
    private String name;
    private int selectedAnswer; // New field to store the selected answer

    public Question(JSONObject jsonObject) throws JSONException {
        this.id = jsonObject.optInt("id", 0);
        this.name = jsonObject.optString("name", "");
        this.selectedAnswer = -1; // Default value indicating no answer selected
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    // Method to get the question's name
    public String getQuestionText() {
        return name;
    }

    // Getter and setter for selectedAnswer
    public int getSelectedAnswer() {
        return selectedAnswer;
    }

    public void setSelectedAnswer(int selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }
}
