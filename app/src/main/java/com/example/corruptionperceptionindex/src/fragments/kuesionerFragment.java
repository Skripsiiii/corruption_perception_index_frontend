package com.example.corruptionperceptionindex.src.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.corruptionperceptionindex.R;
import com.example.corruptionperceptionindex.src.screens.kuesioner.dimenssionQuestion;

public class kuesionerFragment extends Fragment {

    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "QuestionnairePrefs";
    TextView namatahundaerahTV, presentasistatusBar, statusKuesioner, lanjutKuesionerButton, statusisiKuesioner;
    ProgressBar progressstatusBar;
    LinearLayout warnaStatusisi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_kuesioner, container, false);

        namatahundaerahTV = view.findViewById(R.id.tahunDaerahTxt);
        presentasistatusBar = view.findViewById(R.id.progressPresentation);
        progressstatusBar = view.findViewById(R.id.progress_bar);
        warnaStatusisi = view.findViewById(R.id.warnaStatusisi);
        statusKuesioner = view.findViewById(R.id.statusKuesioner);
        lanjutKuesionerButton = view.findViewById(R.id.continueKuesioner);
        statusisiKuesioner = view.findViewById(R.id.belumIsi);

        sharedPreferences = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        // Membaca data dari SharedPreferences
        SharedPreferences prefs = getActivity().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        String tahun = prefs.getString("tahun", "");
        String provinsi = prefs.getString("selectprovinsi", "");
        String kabupaten = prefs.getString("selectkabupaten", "");

        // Set the data to the TextView
        namatahundaerahTV.setText(tahun + " - " + provinsi + " - " + kabupaten);

        // Retrieve the progress
        int progress = sharedPreferences.getInt("progress", 0);
        int clickCounter = sharedPreferences.getInt("clickCounter", 0);

        // Update the ProgressBar and TextView
        progressstatusBar.setProgress(progress);
        presentasistatusBar.setText(progress + "%");

        if (progress == 0) {
            warnaStatusisi.setVisibility(View.GONE);
            statusKuesioner.setVisibility(View.GONE);
            statusisiKuesioner.setVisibility(View.VISIBLE);
        } else {
            warnaStatusisi.setVisibility(View.VISIBLE);
            statusKuesioner.setVisibility(View.VISIBLE);
            statusisiKuesioner.setVisibility(View.GONE);

            // Change background color based on progress
            if (progress > 50) {
                warnaStatusisi.setBackgroundResource(R.drawable.finish_kuesioner);
                statusKuesioner.setText("Kuesioner Yang Belum Selesai");
            } else {
                warnaStatusisi.setBackgroundResource(R.drawable.unfinish_kuesioner);
                statusKuesioner.setText("Kuesioner Yang Belum Selesai");
            }
        }

        if (progress == 100) {
            lanjutKuesionerButton.setVisibility(View.GONE);
        } else {
            lanjutKuesionerButton.setVisibility(View.VISIBLE);
        }

        lanjutKuesionerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), dimenssionQuestion.class);
                intent.putExtra("currentDimensionIndex", clickCounter); // Pass the last completed dimension index
                intent.putExtra("tahun", tahun); // Pass the year
                intent.putExtra("provinsi", provinsi); // Pass the province
                intent.putExtra("kabupaten", kabupaten); // Pass the district
                startActivity(intent);
            }
        });

        return view;
    }
}
