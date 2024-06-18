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
import com.example.corruptionperceptionindex.src.adapter.dataProvinsiThirdAdapter;
import com.example.corruptionperceptionindex.src.connection.FetchDimensionDataTask;
import com.example.corruptionperceptionindex.src.model.DimensionData;
import com.example.corruptionperceptionindex.src.connection.Koneksi;

import java.util.List;

public class dataProvinsiThirdFragment extends Fragment {

    TextView namaProvinsiTextView, namaKabupatenKotaTextView, statusWarnaTextProvinsi, statusPointTextProvinsi, statusWarnaTextKabupatenKota, statusPointTextKabupatenKota;
    ProgressBar progressBarProvinsi, progressBarKabupatenKota;
    View statusBarProvinsi, statusBarKabupatenKota;
    RecyclerView recyclerView;
    private String provinsiName, kabupatenKota;
    private int cityId, indexResult, indexResultKabupatenKota;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_data_provinsi_third_fragment, container, false);

        namaProvinsiTextView = view.findViewById(R.id.namaProvinsi);
        namaKabupatenKotaTextView = view.findViewById(R.id.namakabupatenkota);
        statusWarnaTextProvinsi = view.findViewById(R.id.statusWarnaText);
        statusPointTextProvinsi = view.findViewById(R.id.statusPointText);
        progressBarProvinsi = view.findViewById(R.id.progressBar);
        statusBarProvinsi = view.findViewById(R.id.statusBar);
        statusWarnaTextKabupatenKota = view.findViewById(R.id.statusWarnaTextkabupatenkota);
        statusPointTextKabupatenKota = view.findViewById(R.id.statusPointTextkabupatenkota);
        progressBarKabupatenKota = view.findViewById(R.id.progressBarkabupatenkota);
        statusBarKabupatenKota = view.findViewById(R.id.statusBarkabupatenkota);
        recyclerView = view.findViewById(R.id.recycler_view);

        Bundle args = getArguments();
        if (args != null) {
            provinsiName = args.getString("provinsiName");
            kabupatenKota = args.getString("kabupatenKota");
            cityId = args.getInt("cityId");
            indexResult = args.getInt("indexResult");
            indexResultKabupatenKota = args.getInt("indexResultKabupatenKota");

            namaProvinsiTextView.setText(provinsiName);
            namaKabupatenKotaTextView.setText(kabupatenKota);
            statusPointTextProvinsi.setText(indexResult + "/100");
            progressBarProvinsi.setProgress(indexResult);
            statusPointTextKabupatenKota.setText(indexResultKabupatenKota + "/100");
            progressBarKabupatenKota.setProgress(indexResultKabupatenKota);

            // Setting up the province status
            setStatus(indexResult, statusBarProvinsi, statusWarnaTextProvinsi, progressBarProvinsi, statusPointTextProvinsi, true);

            // Setting up the kabupaten/kota status
            setStatus(indexResultKabupatenKota, statusBarKabupatenKota, statusWarnaTextKabupatenKota, progressBarKabupatenKota, statusPointTextKabupatenKota, false);
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        fetchDimensionData(cityId);

        return view;
    }

    private void fetchDimensionData(int cityId) {
        String dimensionDataUrl = new Koneksi().connDataDimension() + cityId;

        new FetchDimensionDataTask(getContext(), dimensionDataList -> {
            dataProvinsiThirdAdapter adapter = new dataProvinsiThirdAdapter(dimensionDataList);
            adapter.setOnNextButtonClickListener(selectedDimensionData -> {
                dataProvinsiFourthFragment fourthFragment = new dataProvinsiFourthFragment();
                Bundle bundle = new Bundle();
                bundle.putString("provinsiName", provinsiName);
                bundle.putString("kabupatenKota", kabupatenKota);
                bundle.putInt("dimensionId", selectedDimensionData.getId());
                bundle.putString("selectedDimensi", selectedDimensionData.getName());
                bundle.putInt("indexResultProvinsi", indexResult);
                bundle.putInt("indexResultKabupatenKota", indexResultKabupatenKota);
                bundle.putDouble("indexResultDimensi", selectedDimensionData.getIndexResult()); // Pass indexResultDimensi
                fourthFragment.setArguments(bundle);

                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, fourthFragment)
                        .addToBackStack(null)
                        .commit();
            });
            recyclerView.setAdapter(adapter);
        }).execute(dimensionDataUrl);
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
