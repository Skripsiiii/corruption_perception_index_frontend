package com.example.corruptionperceptionindex.src.fragmentsData;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.corruptionperceptionindex.R;
import com.example.corruptionperceptionindex.src.adapter.dataProvinsiSecondAdapter;
import com.example.corruptionperceptionindex.src.connection.FetchCityDataTask;
import com.example.corruptionperceptionindex.src.model.CityData;

import java.util.List;

public class dataProvinsiSecondFragment extends Fragment {

    TextView namaProvinsiTextView, statusWarnaText, statusPointText;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    View statusBar;
    private String provinsiName;
    private int provinceId;
    private int indexResult;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_data_provinsi_second_fragment, container, false);

        namaProvinsiTextView = view.findViewById(R.id.namaProvinsi);
        statusWarnaText = view.findViewById(R.id.statusWarnaText);
        statusPointText = view.findViewById(R.id.statusPointText);
        progressBar = view.findViewById(R.id.progressBar);
        statusBar = view.findViewById(R.id.statusBar);
        recyclerView = view.findViewById(R.id.recycler_view);

        Bundle args = getArguments();
        if (args != null) {
            provinsiName = args.getString("provinsiName");
            provinceId = args.getInt("provinceId");
            indexResult = args.getInt("indexResult");

            namaProvinsiTextView.setText(provinsiName);
            statusPointText.setText(indexResult + "/100");
            progressBar.setProgress(indexResult);

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
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        fetchCityData(provinceId);

        return view;
    }

    private void fetchCityData(int provinceId) {
        String cityDataUrl = "https://6e49-2404-8000-1003-61d1-39cf-8c86-8a14-b8e8.ngrok-free.app/api/cityData/" + provinceId;

        new FetchCityDataTask(cityData -> {
            dataProvinsiSecondAdapter adapter = new dataProvinsiSecondAdapter(cityData, kabupatenKota -> {
                // Load the third fragment and pass the data
                dataProvinsiThirdFragment thirdFragment = new dataProvinsiThirdFragment();
                Bundle bundle = new Bundle();
                bundle.putString("provinsiName", provinsiName);
                bundle.putString("kabupatenKota", kabupatenKota);
                thirdFragment.setArguments(bundle);

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, thirdFragment)
                        .addToBackStack(null)
                        .commit();
            }, provinsiName, indexResult);
            recyclerView.setAdapter(adapter);
        }).execute(cityDataUrl);
    }
}
