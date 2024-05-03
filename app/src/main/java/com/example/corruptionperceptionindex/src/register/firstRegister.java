package com.example.corruptionperceptionindex.src.register;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.corruptionperceptionindex.R;

public class firstRegister extends Fragment {

    com.google.android.material.textfield.TextInputLayout userLayout, emailLayout, passwordLayout, confirmPasswordLayout;
    com.google.android.material.textfield.TextInputEditText namaEditText, emailEditText, passwordEditText, confirmpasswordEditText;
    ImageView unchecked1, checked1, unchecked2, checked2, unchecked3, checked3;
    Button loginButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.activity_first_register, container, false);
        View fragment = inflater.inflate(R.layout.activity_first_register, container, true);

        namaEditText = fragment.findViewById(R.id.nama);
        emailEditText = fragment.findViewById(R.id.email);
        passwordEditText = fragment.findViewById(R.id.password);
        confirmpasswordEditText = fragment.findViewById(R.id.confirmPassword);

        confirmPasswordLayout = fragment.findViewById(R.id.confirmPasswordLayout);

        unchecked1 = fragment.findViewById(R.id.unchaked1);
        unchecked2 = fragment.findViewById(R.id.unchaked2);
        unchecked3 = fragment.findViewById(R.id.unchaked3);

        checked1 = fragment.findViewById(R.id.cheked1);
        checked2 = fragment.findViewById(R.id.cheked2);
        checked3 = fragment.findViewById(R.id.cheked3);

        checked1.setVisibility(View.GONE);
        checked2.setVisibility(View.GONE);
        checked3.setVisibility(View.GONE);

        loginButton = fragment.findViewById(R.id.btn_login);
        loginBtnEnableDisable();

        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkPassword(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        confirmpasswordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkPasswordMatch();
                loginBtnEnableDisable();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        return fragment;
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

    private void checkPasswordMatch() {
        String password = passwordEditText.getText().toString();
        String confirmPassword = confirmpasswordEditText.getText().toString();

        if (!password.equals(confirmPassword)) {
            confirmPasswordLayout.setError("Kata sandi Anda tidak cocok");
        } else {
            confirmPasswordLayout.setError(null);
        }
    }

    private void loginBtnEnableDisable() {
        String nama = namaEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String confirmPassword = confirmpasswordEditText.getText().toString();

        if (!nama.isEmpty() && !email.isEmpty() && !password.isEmpty() && !confirmPassword.isEmpty()) {
            loginButton.setEnabled(true);
        } else {
            loginButton.setEnabled(false);
        }
    }

}
