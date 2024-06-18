package com.example.corruptionperceptionindex.src.connection;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.corruptionperceptionindex.src.model.CityData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FetchCityDataTask extends AsyncTask<String, Void, List<CityData>> {

    private OnCityDataFetchedListener listener;
    private Context context;
    private String token;

    public interface OnCityDataFetchedListener {
        void onCityDataFetched(List<CityData> cityData);
    }

    public FetchCityDataTask(Context context, OnCityDataFetchedListener listener) {
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
    protected List<CityData> doInBackground(String... urls) {
        List<CityData> cityDataList = new ArrayList<>();
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
            JSONArray dataArray = responseJson.getJSONObject("data").getJSONObject("cityCorruptionResults").getJSONArray("data");

            for (int i = 0; i < dataArray.length(); i++) {
                JSONObject cityJson = dataArray.getJSONObject(i);
                int id = cityJson.getInt("id");
                String cityName = cityJson.getString("name");
                double indexResult = cityJson.getDouble("index_result");
                cityDataList.add(new CityData(id, cityName, indexResult));
            }

        } catch (Exception e) {
            Log.e("FetchCityDataTask", "Error fetching city data", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        return cityDataList;
    }

    @Override
    protected void onPostExecute(List<CityData> cityData) {
        if (listener != null) {
            listener.onCityDataFetched(cityData);
        }
    }
}
