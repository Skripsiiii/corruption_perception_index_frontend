package com.example.corruptionperceptionindex.src.screens.login;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.corruptionperceptionindex.R;
import com.example.corruptionperceptionindex.src.components.ShowHidePassword;
import com.example.corruptionperceptionindex.src.components.ValidateEmailPassword;
import com.example.corruptionperceptionindex.src.connection.Koneksi;
import com.example.corruptionperceptionindex.src.fragments.RegisterFragment;
import com.example.corruptionperceptionindex.src.register.thirdRegister;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {

    public String url_login;
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

        // Initialize the API connection URL
        Koneksi url_konesksi = new Koneksi();
        url_login = url_konesksi.connLogin();

        linkRegisterButton.setOnClickListener(v -> {
            Intent registerActivity = new Intent(LoginActivity.this, RegisterFragment.class);
            startActivity(registerActivity);
            finish();
        });

        linkForgotPassword.setOnClickListener(v -> {
            Intent forgotPassActivity = new Intent(LoginActivity.this, ForgotPassActivity.class);
            startActivity(forgotPassActivity);
            finish();
        });

        btnLogin.setOnClickListener(v -> {
            email = editTextEmailAddress.getText().toString();
            password = editTextPassword.getText().toString();

            if (!ValidateEmailPassword.isValidEmail(email)) {
                Toast.makeText(getApplicationContext(), "Tolong masukkan email anda dengan format yang valid.", Toast.LENGTH_SHORT).show();
            } else {
                // Execute the AsyncTask to perform the POST request
                new LoginTask().execute(url_login, email, password);
            }
        });
    }

    private class LoginTask extends AsyncTask<String, Void, String> {

        private int userId;
        private String token;

        @Override
        protected String doInBackground(String... params) {
            String urlString = params[0]; // URL to call
            String email = params[1]; // Email
            String password = params[2]; // Password

            HttpURLConnection urlConnection = null;
            try {
                URL url = new URL(urlString);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json; utf-8");
                urlConnection.setRequestProperty("Accept", "application/json");
                urlConnection.setDoOutput(true);

                // Create the JSON payload
                JSONObject jsonParam = new JSONObject();
                jsonParam.put("email", email);
                jsonParam.put("password", password);

                // Send the JSON payload
                try (OutputStream os = urlConnection.getOutputStream()) {
                    byte[] input = jsonParam.toString().getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                // Read the response
                int code = urlConnection.getResponseCode();
                if (code == HttpURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    String inputLine;
                    StringBuilder response = new StringBuilder();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    JSONObject jsonResponse = new JSONObject(response.toString());
                    if (jsonResponse.getBoolean("success")) {
                        JSONObject userData = jsonResponse.getJSONObject("data").getJSONObject("user");
                        userId = userData.getInt("id");
                        token = jsonResponse.getJSONObject("data").getString("token");
                        return "Login Berhasil";
                    } else {
                        return jsonResponse.getString("message");
                    }
                } else {
                    return "Login Gagal";
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "Exception: " + e.getMessage();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
        }

        @Override
        protected void onPostExecute(String result) {
            // Display the result
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
//            if (result.equals("Login Berhasil")) {
//                // Pass the user ID and step to the RegisterFragment
//                Intent registerActivity = new Intent(LoginActivity.this, RegisterFragment.class);
//                registerActivity.putExtra("USER_ID", userId);
//                registerActivity.putExtra("STEP", 2); // Step 2 berarti halaman ketiga, karena index mulai dari 0
//                startActivity(registerActivity);
//                finish();
//            } else {
//                showAlert(result);
//            }

            if (result.equals("Login Berhasil")) {
                saveData(token);
                SharedPreferences prefs = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("isLoggedIn", true);
                editor.putString("token", token);
                Log.d("TOKEN", token);
                editor.apply();

                // Pass the user ID and step to the RegisterFragment
                Intent registerActivity = new Intent(LoginActivity.this, RegisterFragment.class);
                registerActivity.putExtra("USER_ID", userId);
                registerActivity.putExtra("STEP", 2); // Step 2 berarti halaman ketiga, karena index mulai dari 0
                startActivity(registerActivity);
                finish();
            } else {
                showAlert(result);
            }

        }
    }

    private void showAlert(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Gagal Masuk")
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String password = editTextPassword.getText().toString();
            checkPassword(password);
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
    private void saveData(String token){
        SharedPreferences prefs = LoginActivity.this.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("token", token);
        editor.apply();

    }
}