package com.example.corruptionperceptionindex.src.screens.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.corruptionperceptionindex.R;
import com.example.corruptionperceptionindex.src.components.ShowHidePassword;
import com.example.corruptionperceptionindex.src.components.ValidateEmailPassword;
import com.example.corruptionperceptionindex.src.fragments.RegisterFragment;
import com.example.corruptionperceptionindex.src.register.aktivasiEmail;

public class LoginActivity extends AppCompatActivity {

    EditText editTextEmailAddress, editTextPassword;
    TextView linkRegisterButton, linkForgotPassword;
    Button btnLogin;
    String email, password;
    ImageView showHide;
    ImageView unchecked1, checked1, unchecked2, checked2, unchecked3, checked3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmailAddress = findViewById(R.id.editTextEmailAddress);
        editTextPassword = findViewById(R.id.editTextPassword);
        showHide = findViewById(R.id.showHide);
        btnLogin = findViewById(R.id.btnLogin);
        linkRegisterButton = findViewById(R.id.linkRegister);
        linkForgotPassword = findViewById(R.id.linkForgotPassword);

        showHide.setOnClickListener(view -> ShowHidePassword.onViewIconClick(editTextPassword, showHide));

//        btnLogin.setEnabled(false);

//        btnLogin.setBackgroundColor(R.drawable.rounded_button_disable);

        editTextEmailAddress.addTextChangedListener(textWatcher);
        editTextPassword.addTextChangedListener(textWatcher);

        unchecked1 = findViewById(R.id.unchaked1);
        unchecked2 = findViewById(R.id.unchaked2);
        unchecked3 = findViewById(R.id.unchaked3);

        checked1 = findViewById(R.id.cheked1);
        checked2 = findViewById(R.id.cheked2);
        checked3 = findViewById(R.id.cheked3);

        checked1.setVisibility(View.GONE);
        checked2.setVisibility(View.GONE);
        checked3.setVisibility(View.GONE);

        loginBtnEnableDisable();

        linkRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerActivity = new Intent(LoginActivity.this, RegisterFragment.class);
                startActivity(registerActivity);
                finish();
            }
        });

        linkForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgotPassActivity = new Intent(LoginActivity.this, ForgotPassActivity.class);
                startActivity(forgotPassActivity);
                finish();
            }
        });

        btnLogin.setOnClickListener(v -> {
            email = editTextEmailAddress.getText().toString();
            password = editTextPassword.getText().toString();

            if (!ValidateEmailPassword.isValidEmail(email)) {

                //CUMA COBA AJA
                btnLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent coba = new Intent(LoginActivity.this, aktivasiEmail.class);
                        startActivity(coba);
                    }
                });


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
            String password = editTextPassword.getText().toString();
            checkPassword(password);
            enableLoginButton();
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    private void enableLoginButton() {

        String email = editTextEmailAddress.getText().toString();
        String password = editTextPassword.getText().toString();
        boolean isEmailEmpty = email.isEmpty();
        boolean isPasswordEmpty = password.isEmpty();

        btnLogin.setEnabled(!isEmailEmpty && !isPasswordEmpty);

        int backgroundDrawable = isEmailEmpty || isPasswordEmpty ? R.drawable.rounded_button_disable : R.drawable.rounded_button;
        btnLogin.setBackgroundResource(backgroundDrawable);

    }

    private void loginBtnEnableDisable() {
        String email = editTextEmailAddress.getText().toString();
        String password = editTextPassword.getText().toString();

        if (!email.isEmpty() && !password.isEmpty()) {
            btnLogin.setEnabled(true);
        } else {
            btnLogin.setEnabled(false);
        }
    }

    private void checkPassword(String password) {
        boolean hasUpperCase = !password.equals(password.toLowerCase());
        boolean hasLowerCase = !password.equals(password.toUpperCase());
        boolean hasDigit = password.matches(".*\\d.*");

        if (hasUpperCase) {
            unchecked1.setVisibility(View.GONE);
            checked1.setVisibility(View.VISIBLE);
        } else {
            unchecked1.setVisibility(View.VISIBLE);
            checked1.setVisibility(View.GONE);
        }

        if (password.length() >= 6) {
            unchecked2.setVisibility(View.GONE);
            checked2.setVisibility(View.VISIBLE);
        } else {
            unchecked2.setVisibility(View.VISIBLE);
            checked2.setVisibility(View.GONE);
        }

        if (hasDigit) {
            unchecked3.setVisibility(View.GONE);
            checked3.setVisibility(View.VISIBLE);
        } else {
            unchecked3.setVisibility(View.VISIBLE);
            checked3.setVisibility(View.GONE);
        }
    }

    // Fungsi untuk menentukan apakah kekuatan kata sandi sudah cukup
    private boolean isPasswordStrongEnough(String password) {
        boolean hasUpperCase = !password.equals(password.toLowerCase());
        boolean hasLowerCase = !password.equals(password.toUpperCase());
        boolean hasDigit = password.matches(".*\\d.*");

        return password.length() >= 6 && hasUpperCase && hasLowerCase && hasDigit;
    }

//    private final TextWatcher textWatcher = new TextWatcher() {
//        @Override
//        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//        }
//
//        @Override
//        public void onTextChanged(CharSequence s, int start, int before, int count) {
//            String email = editTextEmailAddress.getText().toString();
//            String password = editTextPassword.getText().toString();
//            boolean isEmailEmpty = email.isEmpty();
//            boolean isPasswordEmpty = password.isEmpty();
//
//            btnLogin.setEnabled(!isEmailEmpty && !isPasswordEmpty);
//
//            int backgroundDrawable = isEmailEmpty || isPasswordEmpty ? R.drawable.rounded_button_disable : R.drawable.rounded_button;
//            btnLogin.setBackgroundResource(backgroundDrawable);
//        }
//
//        @Override
//        public void afterTextChanged(Editable s) {
//
//        }
//    };
}