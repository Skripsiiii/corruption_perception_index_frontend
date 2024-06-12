package com.example.corruptionperceptionindex.src.connection;

import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FetchProvinceIndexTask extends AsyncTask<Void, Void, Double> {

    private final OnFetchCompleteListener listener;

    public interface OnFetchCompleteListener {
        void onFetchComplete(double averageIndexResult);
    }

    public FetchProvinceIndexTask(OnFetchCompleteListener listener) {
        this.listener = listener;
    }

    @Override
    protected Double doInBackground(Void... voids) {
        double totalIndex = 0;
        int count = 0;
        try {
            URL url = new URL(new Koneksi().connDataProvince());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();

            JSONObject jsonResponse = new JSONObject(response.toString());
            JSONArray dataArray = jsonResponse.getJSONObject("data").getJSONArray("data");

            for (int i = 0; i < dataArray.length(); i++) {
                JSONObject dataObject = dataArray.getJSONObject(i);
                double indexResult = dataObject.getDouble("index_result");
                totalIndex += indexResult;
                count++;
            }

        } catch (Exception e) {
            Log.e("FetchProvinceIndexTask", "Error fetching data", e);
        }

        return count > 0 ? totalIndex / count : 0;
    }

    @Override
    protected void onPostExecute(Double averageIndexResult) {
        if (listener != null) {
            listener.onFetchComplete(averageIndexResult);
        }
    }
}
