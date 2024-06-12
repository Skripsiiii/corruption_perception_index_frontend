package com.example.corruptionperceptionindex.src.connection;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FetchQuestionsTask extends AsyncTask<Void, Void, List<String>> {
    private final FetchQuestionsCallback callback;

    public FetchQuestionsTask(FetchQuestionsCallback callback) {
        this.callback = callback;
    }

    @Override
    protected List<String> doInBackground(Void... voids) {
        List<String> questions = new ArrayList<>();
        try {
            Koneksi koneksi = new Koneksi();
            URL url = new URL(koneksi.connquestionsViewpoints());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            in.close();
            conn.disconnect();

            JSONObject jsonResponse = new JSONObject(content.toString());
            JSONArray jsonArray = jsonResponse.getJSONArray("viewpoints");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject questionObject = jsonArray.getJSONObject(i);
                String questionName = questionObject.getString("name");
                questions.add(questionName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return questions;
    }

    @Override
    protected void onPostExecute(List<String> questions) {
        if (callback != null) {
            callback.onQuestionsFetched(questions);
        }
    }

    public interface FetchQuestionsCallback {
        void onQuestionsFetched(List<String> questions);
    }
}
