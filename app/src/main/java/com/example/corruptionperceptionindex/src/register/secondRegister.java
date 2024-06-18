package com.example.corruptionperceptionindex.src.register;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.corruptionperceptionindex.R;
import com.example.corruptionperceptionindex.src.connection.Koneksi;
import com.example.corruptionperceptionindex.src.viewmodel.RegistrationViewModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class secondRegister extends Fragment {
    com.google.android.material.textfield.TextInputLayout jenisklaminLayout, bornLayout, pendidikanLayout, pekerjaanLayout;
    EditText dateEditText;
    Spinner genderSpinner, educationSpiner, occupationSpinner;
    Button saveButton;
    ImageView profileLayout;
    TextView isinama, isiemail, isipassword, isiconfirmpassword;

    private RegistrationViewModel registrationViewModel;

    // Maps to store the IDs of education and occupation items
    private Map<String, Integer> educationMap = new HashMap<>();
    private Map<String, Integer> occupationMap = new HashMap<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_second_register, container, false);

        registrationViewModel = new ViewModelProvider(requireActivity()).get(RegistrationViewModel.class);

        isinama = view.findViewById(R.id.tvnama);
        isiemail = view.findViewById(R.id.tvemail);
        isipassword = view.findViewById(R.id.tvpassword);
        isiconfirmpassword = view.findViewById(R.id.tvconfirmpassword);

        isinama.setVisibility(View.GONE);
        isiemail.setVisibility(View.GONE);
        isipassword.setVisibility(View.GONE);
        isiconfirmpassword.setVisibility(View.GONE);

        genderSpinner = view.findViewById(R.id.genderSpiner);
        dateEditText = view.findViewById(R.id.dateEditText);
        educationSpiner = view.findViewById(R.id.educationSpiner);
        occupationSpinner = view.findViewById(R.id.occupationSpinner);

        jenisklaminLayout = view.findViewById(R.id.jenisklaminLayout);
        bornLayout = view.findViewById(R.id.dateLayout);
        pendidikanLayout = view.findViewById(R.id.pendidikanLayout);
        pekerjaanLayout = view.findViewById(R.id.pekerjaanLayout);

        profileLayout = view.findViewById(R.id.imageLayout);
        profileLayout.setImageResource(R.mipmap.profile2);

        saveButton = view.findViewById(R.id.btn_login);

        // Populate spinners with dummy data
        setupDummyData();

        // Fetch data from API for education spinner
        new FetchEducationDataTask().execute();

        // Fetch data from API for occupation spinner
        new FetchOccupationDataTask().execute();

        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateFieldsAndRegister();
            }
        });

        // Display data from ViewModel
        isinama.setText(registrationViewModel.getName());
        isiemail.setText(registrationViewModel.getEmail());
        isipassword.setText(registrationViewModel.getPassword());
        isiconfirmpassword.setText(registrationViewModel.getPasswordConfirmation());

        return view;
    }

    private void setupDummyData() {
        // Populate gender spinner
        List<String> genderList = new ArrayList<>();
        genderList.add("Pilih Jenis Kelamin");
        genderList.add("Laki-laki");
        genderList.add("Perempuan");
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, genderList);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(genderAdapter);
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String selectedDate = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year);
                        dateEditText.setText(selectedDate);
                    }
                }, year, month, day);
        datePickerDialog.show();
    }

//    private void validateFieldsAndRegister() {
//        jenisklaminLayout.setError(null);
//        bornLayout.setError(null);
//        pendidikanLayout.setError(null);
//        pekerjaanLayout.setError(null);
//
//        String selectedGender = genderSpinner.getSelectedItem().toString();
//        String selectedDate = dateEditText.getText().toString();
//        String selectedEducation = educationSpiner.getSelectedItem().toString();
//        String selectedOccupation = occupationSpinner.getSelectedItem().toString();
//
//        if (selectedGender.equals("Pilih Jenis Kelamin")) {
//            jenisklaminLayout.setError("Pilih jenis kelamin anda");
//        } else if (TextUtils.isEmpty(selectedDate) || selectedDate.equals("DD/MM/YY")) {
//            bornLayout.setError("Pilih tanggal lahir anda");
//        } else if (selectedEducation.equals("Pilih Pendidikan")) {
//            pendidikanLayout.setError("Pilih pendidikan anda");
//        } else if (selectedOccupation.equals("Pilih Pekerjaan")) {
//            pekerjaanLayout.setError("Pilih pekerjaan anda");
//        } else {
//            saveData(selectedGender, selectedDate, selectedEducation, selectedOccupation);
//            new RegisterUserTask().execute();
//        }
//    }


    private void validateFieldsAndRegister() {
        jenisklaminLayout.setError(null);
        bornLayout.setError(null);
        pendidikanLayout.setError(null);
        pekerjaanLayout.setError(null);

        String selectedGender = genderSpinner.getSelectedItem().toString();
        String selectedDate = dateEditText.getText().toString();
        String selectedEducation = educationSpiner.getSelectedItem().toString();
        String selectedOccupation = occupationSpinner.getSelectedItem().toString();

        if (selectedGender.equals("Pilih Jenis Kelamin")) {
            jenisklaminLayout.setError("Pilih jenis kelamin anda");
        } else if (TextUtils.isEmpty(selectedDate) || selectedDate.equals("DD/MM/YY")) {
            bornLayout.setError("Pilih tanggal lahir anda");
        } else if (selectedEducation.equals("Pilih Pendidikan")) {
            pendidikanLayout.setError("Pilih pendidikan anda");
        } else if (selectedOccupation.equals("Pilih Pekerjaan")) {
            pekerjaanLayout.setError("Pilih pekerjaan anda");
        } else {
            // Show ProgressDialog
            final ProgressDialog progressDialog = new ProgressDialog(requireContext());
            progressDialog.setMessage("Harap tunggu");
            progressDialog.setCancelable(false);
            progressDialog.show();

            // Dismiss ProgressDialog after 4 seconds
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressDialog.dismiss();
                    saveData(selectedGender, selectedDate, selectedEducation, selectedOccupation);
                    new RegisterUserTask().execute();
                }
            }, 6000); // 4000 milliseconds = 4 seconds
        }
    }

    private class FetchEducationDataTask extends AsyncTask<Void, Void, List<String>> {
        @Override
        protected List<String> doInBackground(Void... voids) {
            List<String> educationList = new ArrayList<>();
            educationList.add("Pilih Pendidikan");

            try {
                Koneksi koneksi = new Koneksi();
                URL url = new URL(koneksi.connEducation());
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }

                in.close();
                conn.disconnect();

                JSONObject jsonResponse = new JSONObject(content.toString());
                JSONArray jsonArray = jsonResponse.getJSONArray("data");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject educationObject = jsonArray.getJSONObject(i);
                    String educationName = educationObject.getString("name");
                    int educationId = educationObject.getInt("id");
                    educationList.add(educationName);
                    educationMap.put(educationName, educationId);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return educationList;
        }

        @Override
        protected void onPostExecute(List<String> educationList) {
            ArrayAdapter<String> educationAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, educationList);
            educationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            educationSpiner.setAdapter(educationAdapter);

            // Load selected item from SharedPreferences if exists
//            SharedPreferences prefs = requireActivity().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
//            String selectedEducation = prefs.getString("selectedEducation", "");
//            if (!selectedEducation.isEmpty()) {
//                int spinnerPosition = educationAdapter.getPosition(selectedEducation);
//                educationSpiner.setSelection(spinnerPosition);
//            }
        }
    }

    private class FetchOccupationDataTask extends AsyncTask<Void, Void, List<String>> {
        @Override
        protected List<String> doInBackground(Void... voids) {
            List<String> occupationList = new ArrayList<>();
            occupationList.add("Pilih Pekerjaan");

            try {
                Koneksi koneksi = new Koneksi();
                URL url = new URL(koneksi.connOccupation());
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }

                in.close();
                conn.disconnect();

                JSONObject jsonResponse = new JSONObject(content.toString());
                JSONArray jsonArray = jsonResponse.getJSONArray("data");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject occupationObject = jsonArray.getJSONObject(i);
                    String occupationName = occupationObject.getString("name");
                    int occupationId = occupationObject.getInt("id");
                    occupationList.add(occupationName);
                    occupationMap.put(occupationName, occupationId);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return occupationList;
        }

        @Override
        protected void onPostExecute(List<String> occupationList) {
            ArrayAdapter<String> occupationAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, occupationList);
            occupationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            occupationSpinner.setAdapter(occupationAdapter);

//            SharedPreferences prefs = requireActivity().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
//            String selectedOccupation = prefs.getString("selectedOccupation", "");
//            if (!selectedOccupation.isEmpty()) {
//                int spinnerPosition = occupationAdapter.getPosition(selectedOccupation);
//                occupationSpinner.setSelection(spinnerPosition);
//            }
        }
    }

    private class RegisterUserTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            String response = "";

            try {
                Koneksi koneksi = new Koneksi();
                URL url = new URL(koneksi.connRegister());
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json; utf-8");
                conn.setRequestProperty("Accept", "application/json");
                conn.setDoOutput(true);

                JSONObject jsonParam = new JSONObject();
                jsonParam.put("name", isinama.getText().toString());
                jsonParam.put("email", isiemail.getText().toString());
                jsonParam.put("password", isipassword.getText().toString());
                jsonParam.put("password_confirmation", isiconfirmpassword.getText().toString());
                jsonParam.put("gender", genderSpinner.getSelectedItem().toString());
                jsonParam.put("age", 1); // Assuming age is 1

                // Get the selected education and occupation IDs
                int educationId = educationMap.getOrDefault(educationSpiner.getSelectedItem().toString(), -1);
                int occupationId = occupationMap.getOrDefault(occupationSpinner.getSelectedItem().toString(), -1);
                jsonParam.put("education", educationId);
                jsonParam.put("occupation", occupationId);

                try (Writer writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"))) {
                    writer.write(jsonParam.toString());
                }

                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }

                in.close();
                conn.disconnect();

                response = content.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            Toast.makeText(getActivity(), "Registration successful", Toast.LENGTH_SHORT).show();
            // Prepare to send data to aktivasiEmail activity
            Intent aktivasiActivity = new Intent(getContext(), aktivasiEmail.class);
            aktivasiActivity.putExtra("userName", isinama.getText().toString());
            startActivity(aktivasiActivity);
        }

    }

    private void saveData(String gender, String date, String education, String occupation) {
        SharedPreferences prefs = requireActivity().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("selectedGender", gender);
        editor.putString("selectedDate", date);
        editor.putString("selectedEducation", education);
        editor.putString("selectedOccupation", occupation);
        editor.apply();
    }

    private void loadSavedData() {
        SharedPreferences prefs = requireActivity().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        String selectedGender = prefs.getString("selectedGender", "");
        String selectedDate = prefs.getString("selectedDate", "");
        String selectedEducation = prefs.getString("selectedEducation", "");
        String selectedOccupation = prefs.getString("selectedOccupation", "");

        if (!selectedGender.isEmpty()) {
            int spinnerPosition = ((ArrayAdapter<String>) genderSpinner.getAdapter()).getPosition(selectedGender);
            genderSpinner.setSelection(spinnerPosition);
        }

        if (!selectedDate.isEmpty()) {
            dateEditText.setText(selectedDate);
        }

        if (!selectedEducation.isEmpty()) {
            int spinnerPosition = ((ArrayAdapter<String>) educationSpiner.getAdapter()).getPosition(selectedEducation);
            educationSpiner.setSelection(spinnerPosition);
        }

        if (!selectedOccupation.isEmpty()) {
            int spinnerPosition = ((ArrayAdapter<String>) occupationSpinner.getAdapter()).getPosition(selectedOccupation);
            occupationSpinner.setSelection(spinnerPosition);
        }

        // Load selected education and occupation in the respective spinners are handled in onPostExecute of FetchEducationDataTask and FetchOccupationDataTask
    }
}
