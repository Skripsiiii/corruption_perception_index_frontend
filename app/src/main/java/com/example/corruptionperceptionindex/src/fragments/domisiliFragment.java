package com.example.corruptionperceptionindex.src.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.corruptionperceptionindex.R;

public class domisiliFragment extends Fragment {

    TextView kotaTv;
    Button addButton;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_domisili, container, false);

            kotaTv = view.findViewById(R.id.kotaTxt);
            addButton = view.findViewById(R.id.detailButton);

            loadSavedData();

            return view;
        }

    private void loadSavedData() {
        SharedPreferences prefs = requireActivity().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        String provinsi = prefs.getString("selectprovinsi", "");
        String kabupaten = prefs.getString("selectkabupaten", "");

        kotaTv.setText(provinsi + "," + kabupaten);

        }


}