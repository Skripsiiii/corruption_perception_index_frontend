package com.example.corruptionperceptionindex.src.connection;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class FetchMapDataTask extends AsyncTask<Void, Void, Map<String, Map<String, Object>>> {

    private static final String TAG = "FetchMapDataTask";
    private OnMapDataFetchedListener listener;

    public FetchMapDataTask(OnMapDataFetchedListener listener) {
        this.listener = listener;
    }

    public interface OnMapDataFetchedListener {
        void onMapDataFetched(Map<String, Map<String, Object>> mapData);
    }

    @Override
    protected Map<String, Map<String, Object>> doInBackground(Void... voids) {
        Map<String, Map<String, Object>> mapData = new HashMap<>();
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(new Koneksi().connqmap());
            Log.d(TAG, "Connecting to URL: " + url.toString());

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(15000); // 15 seconds
            connection.setReadTimeout(15000); // 15 seconds

            int responseCode = connection.getResponseCode();
            Log.d(TAG, "Response Code: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                Log.d(TAG, "Response: " + response.toString());

                JSONObject jsonResponse = new JSONObject(response.toString());
                JSONArray dataArray = jsonResponse.getJSONArray("data");

                for (int i = 0; i < dataArray.length(); i++) {
                    JSONObject dataObject = dataArray.getJSONObject(i);
                    String state = dataObject.getString("state");
                    String coordinates = dataObject.getString("coordinates");
                    int level = dataObject.getInt("level"); // Assuming 'level' is part of the response

                    Map<String, Object> stateData = new HashMap<>();
                    stateData.put("coordinates", coordinates);
                    stateData.put("level", level);

                    mapData.put(state, stateData);
                    Log.d(TAG, "State: " + state + ", Coordinates: " + coordinates + ", Level: " + level);
                }
            } else {
                Log.e(TAG, "Failed to fetch data. Response Code: " + responseCode);
            }

        } catch (Exception e) {
            Log.e(TAG, "Error fetching map data", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                    Log.e(TAG, "Error closing reader", e);
                }
            }
            if (connection != null) {
                connection.disconnect();
            }
        }

        return mapData;
    }

    @Override
    protected void onPostExecute(Map<String, Map<String, Object>> mapData) {
        if (listener != null) {
            listener.onMapDataFetched(mapData);
        }
    }
}
