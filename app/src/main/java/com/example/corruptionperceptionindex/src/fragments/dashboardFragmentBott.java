package com.example.corruptionperceptionindex.src.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.corruptionperceptionindex.R;
import com.example.corruptionperceptionindex.src.screens.main.MainMenu;

public class dashboardFragmentBott extends Fragment {

    Button kuesionerButton, dataButton, mapButton;
    TextView greetingsTv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dashboard_bott, container, false);

        kuesionerButton = view.findViewById(R.id.kuesionerButton);
        dataButton = view.findViewById(R.id.detailButton);
        mapButton = view.findViewById(R.id.mapingButton);
        greetingsTv = view.findViewById(R.id.welcomeName);

        loadSavedData();


        kuesionerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null) {
                    ((MainMenu) getActivity()).selectBottomNavItem(R.id.kuesionerBottom);
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, new kuesionerFragmenBott())
                            .addToBackStack(null)
                            .commit();
                }
            }
        });

        dataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null) {
                    ((MainMenu) getActivity()).selectBottomNavItem(R.id.dataBottom);
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, new dataFragmentBott())
                            .addToBackStack(null)
                            .commit();
//                    title.setText("Data Korupsi Provinsi");
                }
            }
        });

        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null) {
                    ((MainMenu) getActivity()).selectBottomNavItem(R.id.mapBottom);
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, new mapFragmentBott())
                            .addToBackStack(null)
                            .commit();
//                    title.setText("Peta");
                }
            }
        });

        return view;
    }


    private void loadSavedData() {
        SharedPreferences prefs = requireActivity().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        String name = prefs.getString("name", "");

        greetingsTv.setText("Halo " +name);

    }
}
