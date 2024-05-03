package com.example.corruptionperceptionindex.src.screens.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.corruptionperceptionindex.R;
import com.example.corruptionperceptionindex.src.components.ShowHidePassword;
import com.example.corruptionperceptionindex.src.components.ValidateEmailPassword;

public class LoginActivity extends AppCompatActivity {

     EditText editTextEmailAddress, editTextPassword;
     Button btnLogin;
     String email, password;
     ImageView showHide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmailAddress = findViewById(R.id.editTextEmailAddress);
        editTextPassword = findViewById(R.id.editTextPassword);
        showHide = findViewById(R.id.showHide);
        btnLogin = findViewById(R.id.btnLogin);

        showHide.setOnClickListener(view -> ShowHidePassword.onViewIconClick(editTextPassword, showHide));

        btnLogin.setEnabled(false);

        btnLogin.setBackgroundColor(R.drawable.rounded_button_disable);

        editTextEmailAddress.addTextChangedListener(textWatcher);
        editTextPassword.addTextChangedListener(textWatcher);

        btnLogin.setOnClickListener(v -> {
            email = editTextEmailAddress.getText().toString();
            password = editTextPassword.getText().toString();

            if (!ValidateEmailPassword.isValidEmail(email)) {
                Toast.makeText(getApplicationContext(), "Tolong masukkan email  anda dengan format yang valid.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Login Berhasil", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String email = editTextEmailAddress.getText().toString();
            String password = editTextPassword.getText().toString();
            boolean isEmailEmpty = email.isEmpty();
            boolean isPasswordEmpty = password.isEmpty();

            btnLogin.setEnabled(!isEmailEmpty && !isPasswordEmpty);

            int backgroundDrawable = isEmailEmpty || isPasswordEmpty ? R.drawable.rounded_button_disable : R.drawable.rounded_button;
            btnLogin.setBackgroundResource(backgroundDrawable);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}