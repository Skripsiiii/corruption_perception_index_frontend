//package com.example.corruptionperceptionindex.src.connection;
//
//import android.os.AsyncTask;
//
//import com.example.corruptionperceptionindex.src.model.Indicator;
//import com.example.corruptionperceptionindex.src.model.Question;
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.List;
//
//public class FetchDim1QuestionsTask extends AsyncTask<Void, Void, FetchDim1QuestionsTask.Result> {
//
//    private String apiUrl;
//    private OnQuestionsFetchedListener listener;
//
//    public interface OnQuestionsFetchedListener {
//        void onQuestionsFetched(Result result);
//    }
//
//    public static class Result {
//        public List<Indicator> indicators;
//        public String dimensionTitle;
//
//        public Result(List<Indicator> indicators, String dimensionTitle) {
//            this.indicators = indicators;
//            this.dimensionTitle = dimensionTitle;
//        }
//    }
//
//    public FetchDim1QuestionsTask(String apiUrl, OnQuestionsFetchedListener listener) {
//        this.apiUrl = apiUrl;
//        this.listener = listener;
//    }
//
//    @Override
//    protected Result doInBackground(Void... voids) {
//        List<Indicator> indicators = new ArrayList<>();
//        String dimensionTitle = null;
//        try {
//            URL url = new URL(apiUrl);
//            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//            urlConnection.setRequestMethod("GET");
//
//            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
//            String inputLine;
//            StringBuilder content = new StringBuilder();
//
//            while ((inputLine = in.readLine()) != null) {
//                content.append(inputLine);
//            }
//
//            in.close();
//            urlConnection.disconnect();
//
//            JSONObject jsonResponse = new JSONObject(content.toString());
//            JSONArray dataArray = jsonResponse.getJSONArray("data");
//
//            for (int i = 0; i < dataArray.length(); i++) {
//                JSONObject dimensionObject = dataArray.getJSONObject(i);
//                if (dimensionObject.getString("dimension_number").equals("DIM_1")) {
//                    dimensionTitle = dimensionObject.getString("name");
//                    JSONArray indicatorsArray = dimensionObject.getJSONArray("indicators");
//                    for (int j = 0; j < indicatorsArray.length(); j++) {
//                        JSONObject indicatorObject = indicatorsArray.getJSONObject(j);
//                        String indicatorName = indicatorObject.getString("name");
//                        List<Question> questions = new ArrayList<>();
//                        JSONArray questionsArray = indicatorObject.getJSONArray("questions");
//                        for (int k = 0; k < questionsArray.length(); k++) {
//                            JSONObject questionObject = questionsArray.getJSONObject(k);
//                            String questionText = questionObject.getString("name");
//                            questions.add(new Question(questionText));
//                        }
//                        indicators.add(new Indicator(indicatorName, questions));
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return new Result(indicators, dimensionTitle);
//    }
//
//    @Override
//    protected void onPostExecute(Result result) {
//        if (listener != null) {
//            listener.onQuestionsFetched(result);
//        }
//    }
//}
