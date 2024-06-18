package com.example.corruptionperceptionindex.src.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.corruptionperceptionindex.R;

import java.util.Objects;

public class changePassFragment extends Fragment {

    TextView greetingsTv, emailTv;
    EditText emailEdt, oldpassEdt, newpassEdt, confirmPassedt;
    Button saveButton;
    com.google.android.material.textfield.TextInputLayout newPassLayOut, confirmPassLayOut;

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
        saveButton = view.findViewById(R.id.btnSavePass);
        newPassLayOut = view.findViewById(R.id.newPassLayout);
        confirmPassLayOut = view.findViewById(R.id.confirmPassLayout);

        loadSavedData();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newPassLayOut.setError("");
                confirmPassLayOut.setError("");
                String newPass = Objects.requireNonNull(newpassEdt.getText()).toString();
                String confirmPass = Objects.requireNonNull(confirmPassedt.getText()).toString();
                if (TextUtils.isEmpty(newPass)) {
                    newPassLayOut.setError("Password tidak boleh kosong");
                }else if (TextUtils.isEmpty(confirmPass)) {
                    confirmPassLayOut.setError("Password tidak boleh kosong");
                }else {
                    showConfirmationDialog();
                }
            }
        });

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

    private void showConfirmationDialog() {
        new AlertDialog.Builder(requireContext())
                .setTitle("Konfirmasi")
                .setMessage("Anda yakin?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Code to save the new password
                        saveNewPassword();
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .show();
    }

    private void saveNewPassword() {
        String newPassword = newpassEdt.getText().toString();
        SharedPreferences prefs = requireActivity().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("password", newPassword);
        editor.apply();
    }
}
