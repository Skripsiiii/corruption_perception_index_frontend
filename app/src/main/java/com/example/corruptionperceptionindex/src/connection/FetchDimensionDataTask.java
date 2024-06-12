package com.example.corruptionperceptionindex.src.connection;

import android.os.AsyncTask;
import android.util.Log;

import com.example.corruptionperceptionindex.src.model.DimensionData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FetchDimensionDataTask extends AsyncTask<String, Void, List<DimensionData>> {

    private OnDimensionDataFetchedListener listener;

    public interface OnDimensionDataFetchedListener {
        void onDimensionDataFetched(List<DimensionData> dimensionData);
    }

    public FetchDimensionDataTask(OnDimensionDataFetchedListener listener) {
        this.listener = listener;
    }

    @Override
    protected List<DimensionData> doInBackground(String... urls) {
        List<DimensionData> dimensionDataList = new ArrayList<>();
        HttpURLConnection urlConnection = null;

        try {
            URL url = new URL(urls[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            InputStream inputStream = urlConnection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder result = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            JSONObject responseJson = new JSONObject(result.toString());
            JSONArray dataArray = responseJson.getJSONObject("dimensionCorruptionResults").getJSONArray("data");

            for (int i = 0; i < dataArray.length(); i++) {
                JSONObject dimensionJson = dataArray.getJSONObject(i);
                int id = dimensionJson.getInt("id");
                String name = dimensionJson.getString("name");
                double indexResult = dimensionJson.getDouble("index_result");
                dimensionDataList.add(new DimensionData(id, name, indexResult));
            }

        } catch (Exception e) {
            Log.e("FetchDimensionDataTask", "Error fetching dimension data", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        return dimensionDataList;
    }

    @Override
    protected void onPostExecute(List<DimensionData> dimensionData) {
        if (listener != null) {
            listener.onDimensionDataFetched(dimensionData);
        }
    }
}
