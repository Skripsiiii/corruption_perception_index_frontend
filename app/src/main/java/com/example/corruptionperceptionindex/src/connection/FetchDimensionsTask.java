package com.example.corruptionperceptionindex.src.connection;

import android.os.AsyncTask;

import com.example.corruptionperceptionindex.src.model.Dimension;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FetchDimensionsTask extends AsyncTask<Void, Void, List<Dimension>> {

    private String apiUrl;
    private OnDimensionsFetchedListener listener;

    public interface OnDimensionsFetchedListener {
        void onDimensionsFetched(List<Dimension> dimensions);
    }

    public FetchDimensionsTask(String apiUrl, OnDimensionsFetchedListener listener) {
        this.apiUrl = apiUrl;
        this.listener = listener;
    }

    @Override
    protected List<Dimension> doInBackground(Void... voids) {
        List<Dimension> dimensionsList = new ArrayList<>();

        try {
            URL url = new URL(apiUrl);
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
            JSONArray jsonArray = jsonResponse.getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject dimensionObject = jsonArray.getJSONObject(i);
                Dimension dimension = new Dimension(dimensionObject);
                dimensionsList.add(dimension);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dimensionsList;
    }

    @Override
    protected void onPostExecute(List<Dimension> dimensions) {
        if (listener != null) {
            listener.onDimensionsFetched(dimensions);
        }
    }
}
