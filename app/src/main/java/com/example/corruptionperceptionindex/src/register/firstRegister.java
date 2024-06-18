package com.example.corruptionperceptionindex.src.register;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.corruptionperceptionindex.R;
import com.example.corruptionperceptionindex.src.components.ValidateEmailPassword;
import com.example.corruptionperceptionindex.src.viewmodel.RegistrationViewModel;

import java.util.Objects;

public class firstRegister extends Fragment {

    private RegistrationViewModel registrationViewModel;
    com.google.android.material.textfield.TextInputLayout userLayout, emailLayout, passwordLayout, confirmPasswordLayout;
    com.google.android.material.textfield.TextInputEditText namaEditText, emailEditText, passwordEditText, confirmpasswordEditText;
    ImageView unchecked1, checked1, unchecked2, checked2, unchecked3, checked3;
    Button loginButton;
    ImageView profileLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.activity_first_register, container, false);

        registrationViewModel = new ViewModelProvider(requireActivity()).get(RegistrationViewModel.class);

        namaEditText = fragment.findViewById(R.id.nama);
        emailEditText = fragment.findViewById(R.id.email);
        passwordEditText = fragment.findViewById(R.id.password);
        confirmpasswordEditText = fragment.findViewById(R.id.confirmPassword);
        profileLayout = fragment.findViewById(R.id.imageLayout);
        userLayout = fragment.findViewById(R.id.namaLayout);
        passwordLayout = fragment.findViewById(R.id.passwordLayout);
        emailLayout = fragment.findViewById(R.id.emailLayout);
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

        profileLayout.setImageResource(R.mipmap.profile1);

        // Load saved data
        loadSavedData();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLayout.setError("");
                passwordLayout.setError("");
                emailLayout.setError("");
                confirmPasswordLayout.setError("");
                String username = Objects.requireNonNull(namaEditText.getText()).toString();
                String password = Objects.requireNonNull(passwordEditText.getText()).toString();
                String email = Objects.requireNonNull(emailEditText.getText()).toString();
                String confirmPassword = Objects.requireNonNull(confirmpasswordEditText.getText()).toString();

                if (TextUtils.isEmpty(username)) {
                    userLayout.setError("Username tidak boleh kosong");
                } else if (TextUtils.isEmpty(email)) {
                    emailLayout.setError("Email tidak boleh kosong");
                } else if (TextUtils.isEmpty(password)) {
                    passwordLayout.setError("Password tidak boleh kosong");
                } else if (TextUtils.isEmpty(confirmPassword)) {
                    confirmPasswordLayout.setError("Password tidak boleh kosong");
                } else {
                    if (!ValidateEmailPassword.isValidEmail(email)) {
                        Toast.makeText(requireActivity(), "Tolong masukkan email anda dengan format yang valid.", Toast.LENGTH_SHORT).show();
                    } else if (!password.equals(confirmPassword)) {
                        Toast.makeText(requireActivity(), "Konfirmasi kata sandi tidak sesuai dengan kata sandi", Toast.LENGTH_SHORT).show();
                    } else {
                        // Save data to ViewModel
                        registrationViewModel.setName(username);
                        registrationViewModel.setEmail(email);
                        registrationViewModel.setPassword(password);
                        registrationViewModel.setPasswordConfirmation(confirmPassword);

                        // Save data to SharedPreferences
                        saveData(username, email, password, confirmPassword);

                        SharedPreferences prefs = requireActivity().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putBoolean("isLoggedIn", true);
                        editor.apply();

                        // Navigate to the next page
                        ViewPager2 viewPager = requireActivity().findViewById(R.id.viewPager);
                        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
                    }
                }
            }
        });


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

    private void saveData(String name, String email, String password, String confirmPassword) {
        SharedPreferences prefs = requireActivity().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("name", name);
        editor.putString("email", email);
        editor.putString("password", password);
        editor.putString("confirmPassword", confirmPassword);
        editor.apply();
    }

    private void loadSavedData() {
        SharedPreferences prefs = requireActivity().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        String name = prefs.getString("name", "");
        String email = prefs.getString("email", "");
        String password = prefs.getString("password", "");
        String confirmPassword = prefs.getString("confirmPassword", "");

//        namaEditText.setText(name);
//        emailEditText.setText(email);
//        passwordEditText.setText(password);
//        confirmpasswordEditText.setText(confirmPassword);
    }
}
