package com.example.corruptionperceptionindex.src.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.corruptionperceptionindex.R;

public class changePassFragment extends Fragment {

    TextView greetingsTv, emailTv;
    EditText emailEdt, oldpassEdt, newpassEdt, confirmPassedt;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_pass, container, false);

        greetingsTv = view.findViewById(R.id.nametxt);
        emailTv = view.findViewById(R.id.emailTxt);
        emailEdt = view.findViewById(R.id.emailEditText);
        oldpassEdt = view.findViewById(R.id.passEditText);
        newpassEdt = view.findViewById(R.id.newPassEditText);
        confirmPassedt = view.findViewById(R.id.cinfirmPassEditText);

        loadSavedData();

        return view;
    }
    private void loadSavedData() {
        SharedPreferences prefs = requireActivity().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        String name = prefs.getString("name", "");
        String email = prefs.getString("email", "");
        String password = prefs.getString("password", "");

        greetingsTv.setText(name);
        emailTv.setText(email);
        emailEdt.setText(email);
        oldpassEdt.setText(password);

    }

}