package com.example.corruptionperceptionindex.src.connection;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

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
    private Context context;
    private String token;

    public interface OnDimensionDataFetchedListener {
        void onDimensionDataFetched(List<DimensionData> dimensionData);
    }

    public FetchDimensionDataTask(Context context, OnDimensionDataFetchedListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        SharedPreferences prefs = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        token = prefs.getString("token", null);
        Log.d("token", token);
        if (token == null) {
            Toast.makeText(context, "Anda belum melakukan Login", Toast.LENGTH_SHORT).show();
            cancel(true);
        }
    }

    @Override
    protected List<DimensionData> doInBackground(String... urls) {
        List<DimensionData> dimensionDataList = new ArrayList<>();
        HttpURLConnection urlConnection = null;

        try {
            URL url = new URL(urls[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Authorization", "Bearer " + token);
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
