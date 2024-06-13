package com.example.corruptionperceptionindex.src.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.corruptionperceptionindex.R;
import com.example.corruptionperceptionindex.src.connection.FetchProvinceIndexTask;
import com.example.corruptionperceptionindex.src.screens.mainPage.MainMenu;

public class dashboardFragmentBott extends Fragment {

    Button kuesionerButton, dataButton, mapButton;
    TextView greetingsTv, scoreCPI, statusCPI;
    ProgressBar resulProgress;
    View statusWarnaCPI;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard_bott, container, false);

        kuesionerButton = view.findViewById(R.id.kuesionerButton);
        dataButton = view.findViewById(R.id.detailButton);
        mapButton = view.findViewById(R.id.mapingButton);
        greetingsTv = view.findViewById(R.id.welcomeName);
        scoreCPI = view.findViewById(R.id.nilaiCPI);
        statusCPI = view.findViewById(R.id.statusCPI);
        resulProgress = view.findViewById(R.id.circular_progress_bar);
        statusWarnaCPI = view.findViewById(R.id.statusWarnaCPI);

        loadSavedData();
        fetchProvinceIndexData();

        kuesionerButton.setOnClickListener(v -> {
            if (getActivity() != null) {
                ((MainMenu) getActivity()).selectBottomNavItem(R.id.kuesionerBottom);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new kuesionerFragmenBott())
                        .addToBackStack(null)
                        .commit();
            }
        });

        dataButton.setOnClickListener(v -> {
            if (getActivity() != null) {
                ((MainMenu) getActivity()).selectBottomNavItem(R.id.dataBottom);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new dataFragmentBott())
                        .addToBackStack(null)
                        .commit();
            }
        });

        mapButton.setOnClickListener(v -> {
            if (getActivity() != null) {
                ((MainMenu) getActivity()).selectBottomNavItem(R.id.mapBottom);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new mapFragmentBott())
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }

    private void loadSavedData() {
        SharedPreferences prefs = requireActivity().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        String name = prefs.getString("name", "");
        greetingsTv.setText("Halo " + name);
    }

    private void fetchProvinceIndexData() {
        new FetchProvinceIndexTask(averageIndexResult -> {
            scoreCPI.setText(String.format("%.2f", averageIndexResult));
            setProgressBarColorAndStatus(averageIndexResult);
        }).execute();
    }

    private void setProgressBarColorAndStatus(double indexResult) {
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

        resulProgress.getProgressDrawable().setColorFilter(color, PorterDuff.Mode.SRC_IN);
        statusWarnaCPI.setBackgroundColor(color);
        statusCPI.setText(statusText);
    }
}
