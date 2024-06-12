package com.example.corruptionperceptionindex.src.fragmentsData;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.corruptionperceptionindex.R;
import com.example.corruptionperceptionindex.src.adapter.dataProvinsiFourthAdapter;
import com.example.corruptionperceptionindex.src.connection.Koneksi;
import com.example.corruptionperceptionindex.src.model.IndicatorData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class dataProvinsiFourthFragment extends Fragment {
    TextView namaProvinsiTextView, namaKabupatenKotaTextView, dimensiTextView, statusWarnaTextProvinsi, statusWarnaTextKabupatenKota, statusPointTextProvinsi, statusPointTextKabupatenKota;
    ProgressBar progressBarProvinsi, progressBarKabupatenKota;
    View statusBarProvinsi, statusBarKabupatenKota;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_data_provinsi_fourth_fragment, container, false);

        namaProvinsiTextView = view.findViewById(R.id.namaProvinsi);
        namaKabupatenKotaTextView = view.findViewById(R.id.namakabupatenkota);
        dimensiTextView = view.findViewById(R.id.dimensiTxt);
        statusWarnaTextProvinsi = view.findViewById(R.id.statusWarnaText);
        statusPointTextProvinsi = view.findViewById(R.id.statusPointText);
        progressBarProvinsi = view.findViewById(R.id.progressBar);
        statusBarProvinsi = view.findViewById(R.id.statusBar);
        statusWarnaTextKabupatenKota = view.findViewById(R.id.statusWarnaTextkabupatenkota);
        statusPointTextKabupatenKota = view.findViewById(R.id.statusPointTextkabupatenkota);
        progressBarKabupatenKota = view.findViewById(R.id.progressBarkabupatenkota);
        statusBarKabupatenKota = view.findViewById(R.id.statusBarkabupatenkota);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Bundle args = getArguments();
        if (args != null) {
            String provinsiName = args.getString("provinsiName");
            String kabupatenKota = args.getString("kabupatenKota");
            String dimensi = args.getString("selectedDimensi");
            int dimensionId = args.getInt("dimensionId");
            int indexResultProvinsi = args.getInt("indexResultProvinsi");
            int indexResultKabupatenKota = args.getInt("indexResultKabupatenKota");

            namaProvinsiTextView.setText(provinsiName);
            namaKabupatenKotaTextView.setText(kabupatenKota);
            dimensiTextView.setText(dimensi);
            statusPointTextProvinsi.setText(indexResultProvinsi + "/100");
            progressBarProvinsi.setProgress(indexResultProvinsi);
            statusPointTextKabupatenKota.setText(indexResultKabupatenKota + "/100");
            progressBarKabupatenKota.setProgress(indexResultKabupatenKota);

            setStatus(indexResultProvinsi, statusBarProvinsi, statusWarnaTextProvinsi, progressBarProvinsi, statusPointTextProvinsi, true);
            setStatus(indexResultKabupatenKota, statusBarKabupatenKota, statusWarnaTextKabupatenKota, progressBarKabupatenKota, statusPointTextKabupatenKota, false);

            fetchIndicatorData(dimensionId);
        }

        return view;
    }

    private void fetchIndicatorData(int dimensionId) {
        String url = new Koneksi().connDataIndikator() + dimensionId;
        new FetchIndicatorDataTask().execute(url);
    }

    private class FetchIndicatorDataTask extends AsyncTask<String, Void, List<IndicatorData>> {

        @Override
        protected List<IndicatorData> doInBackground(String... strings) {
            String urlString = strings[0];
            List<IndicatorData> indicatorDataList = new ArrayList<>();

            try {
                URL url = new URL(urlString);
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
                JSONArray dataArray = jsonResponse.getJSONObject("indicatorCorruptionResults").getJSONArray("data");

                for (int i = 0; i < dataArray.length(); i++) {
                    JSONObject dataObject = dataArray.getJSONObject(i);
                    int id = dataObject.getInt("id");
                    String name = dataObject.getString("name");
                    int indexResult = dataObject.getInt("index_result");

                    indicatorDataList.add(new IndicatorData(id, name, indexResult));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return indicatorDataList;
        }

        @Override
        protected void onPostExecute(List<IndicatorData> indicatorDataList) {
            dataProvinsiFourthAdapter adapter = new dataProvinsiFourthAdapter(indicatorDataList);
            recyclerView.setAdapter(adapter);
        }
    }

    private void setStatus(int indexResult, View statusBar, TextView statusWarnaText, ProgressBar progressBar, TextView statusPointText, boolean changeTextColor) {
        int color;
        String statusText;
        if (indexResult <= 20) {
            color = 0xFFe76f51; // Red
            statusText = "Sangat Korup";
        } else if (indexResult <= 40) {
            color = 0xFFf4a261; // Orange
            statusText = "Korup";
        } else if (indexResult <= 60) {
            color = 0xFFffd966; // Yellow
            statusText = "Netral";
        } else if (indexResult <= 80) {
            color = 0xFF90c8ac; // Light Green
            statusText = "Aman";
        } else {
            color = 0xFF69b3a2; // Green
            statusText = "Sangat Aman";
        }

        statusBar.setBackgroundColor(color);
        statusWarnaText.setBackgroundColor(color);
        statusWarnaText.setText(statusText);

        // Customize ProgressBar color
        LayerDrawable progressDrawable = (LayerDrawable) progressBar.getProgressDrawable();
        Drawable progressLayer = progressDrawable.findDrawableByLayerId(android.R.id.progress);
        progressLayer.setColorFilter(color, PorterDuff.Mode.SRC_IN);

        // Update status point text color if changeTextColor is true
        if (changeTextColor) {
            statusPointText.setTextColor(color);
        } else {
            statusPointText.setTextColor(getResources().getColor(android.R.color.black));
            statusPointTextProvinsi.setTextColor(getResources().getColor(android.R.color.black));
        }
    }
}
