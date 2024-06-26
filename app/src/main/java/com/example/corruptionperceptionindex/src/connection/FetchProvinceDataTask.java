package com.example.corruptionperceptionindex.src.connection;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.corruptionperceptionindex.src.model.ProvinceData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FetchProvinceDataTask extends AsyncTask<String, Void, List<ProvinceData>> {

    private OnProvinceDataFetchedListener listener;
    private Context context;
    private String token;

    public interface OnProvinceDataFetchedListener {
        void onProvinceDataFetched(List<ProvinceData> provinceData);
    }

    public FetchProvinceDataTask(Context context, OnProvinceDataFetchedListener listener) {
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
    protected List<ProvinceData> doInBackground(String... urls) {
        List<ProvinceData> provinceDataList = new ArrayList<>();
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
            JSONArray dataArray = responseJson.getJSONObject("data").getJSONArray("data");

            for (int i = 0; i < dataArray.length(); i++) {
                JSONObject provinceJson = dataArray.getJSONObject(i);
                int id = provinceJson.getInt("id");
                String provinceName = provinceJson.getString("name");
                double indexResult = provinceJson.getDouble("index_result");
                provinceDataList.add(new ProvinceData(id, provinceName, indexResult));
            }

        } catch (Exception e) {
            Log.e("FetchProvinceDataTask", "Error fetching province data", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        return provinceDataList;
    }

    @Override
    protected void onPostExecute(List<ProvinceData> provinceData) {
        if (listener != null) {
            listener.onProvinceDataFetched(provinceData);
        }
    }
}
