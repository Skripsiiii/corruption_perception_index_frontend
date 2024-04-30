package com.example.corruptionperceptionindex.src.register;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.corruptionperceptionindex.R;

public class firstRegister extends Fragment {

    com.google.android.material.textfield.TextInputLayout userLayout, emailLayout, passwordLayout, confirmPasswordLayout;
    com.google.android.material.textfield.TextInputEditText namaEditText, emailEditText, passwordEditText, confirmpasswordEditText;
    Button loginButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.activity_first_register, container, false);
        View fragment =  inflater.inflate(R.layout.activity_first_register, container, true);

        namaEditText = fragment.findViewById(R.id.nama);
        emailEditText = fragment.findViewById(R.id.email);
        passwordEditText = fragment.findViewById(R.id.password);
        confirmpasswordEditText = fragment.findViewById(R.id.confirmPassword);

        return fragment;
    }
}
