package com.example.corruptionperceptionindex.src.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.corruptionperceptionindex.R;
import com.example.corruptionperceptionindex.src.connection.Koneksi;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class domisiliFragment extends Fragment {

    TextView kotaTv, sinceDate;
    Button addButton;
    Calendar calendar;
    Map<String, Integer> provinceIdMap = new HashMap<>();
    Map<String, Integer> cityIdMap = new HashMap<>();
    Spinner provinceSpinner, citySpinner;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_domisili, container, false);

        kotaTv = view.findViewById(R.id.kotaTxt);
        addButton = view.findViewById(R.id.detailButton);
        calendar = Calendar.getInstance();
        sinceDate = view.findViewById(R.id.tahunTxt);

        loadSavedData();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLocationPickerDialog();
            }
        });

        return view;
    }

    private void loadSavedData() {
        SharedPreferences prefs = requireActivity().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        String provinsi = prefs.getString("selectprovinsi", "");
        String kabupaten = prefs.getString("selectkabupaten", "");

        kotaTv.setText(provinsi + "," + kabupaten);
    }

    private void showLocationPickerDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_location_picker, null);
        builder.setView(dialogView);

        provinceSpinner = dialogView.findViewById(R.id.provinceSpinner);
        citySpinner = dialogView.findViewById(R.id.citySpinner);
        final EditText dateEditText = dialogView.findViewById(R.id.dateEditText);
        final ImageView calendarIcon = dialogView.findViewById(R.id.calendarIcon);

        final TextInputLayout provinceLayout = dialogView.findViewById(R.id.provinsiLayout);
        final TextInputLayout cityLayout = dialogView.findViewById(R.id.cityLayout);
        final TextInputLayout dateLayout = dialogView.findViewById(R.id.dateLayout);

        new FetchProvincesTask().execute();

        provinceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedProvince = (String) parent.getItemAtPosition(position);
                if (provinceIdMap.containsKey(selectedProvince)) {
                    int provinceId = provinceIdMap.get(selectedProvince);
                    new FetchCitiesTask(provinceId).execute();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        View.OnClickListener dateClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(dateEditText);
            }
        };

        calendarIcon.setOnClickListener(dateClickListener);
        dateEditText.setOnClickListener(dateClickListener);

        builder.setPositiveButton("OK", null);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validation logic
                boolean valid = true;

                String selectedProvince = (String) provinceSpinner.getSelectedItem();
                String selectedCity = (String) citySpinner.getSelectedItem();
                String selectedDate = dateEditText.getText().toString();

                if (selectedProvince == null || selectedProvince.equals("Pilih Provinsi")) {
                    provinceLayout.setError("Provinsi tidak boleh kosong");
                    valid = false;
                } else {
                    provinceLayout.setError(null);
                }

                if (selectedCity == null || selectedCity.equals("Pilih Kabupaten/Kota")) {
                    cityLayout.setError("Kabupaten/Kota tidak boleh kosong");
                    valid = false;
                } else {
                    cityLayout.setError(null);
                }

                if (selectedDate == null || selectedDate.equals("DD/MM/YY")) {
                    dateLayout.setError("Pilih tanggal mulai");
                    valid = false;
                } else {
                    dateLayout.setError(null);
                }

                if (valid) {
                    // Save the selected data in SharedPreferences
                    SharedPreferences prefs = requireActivity().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("selectprovinsi", selectedProvince);
                    editor.putString("selectkabupaten", selectedCity);
                    editor.putString("selectedDate", selectedDate);
                    editor.apply();

                    // Update TextView with the selected data
                    kotaTv.setText(selectedProvince + "," + selectedCity);
                    sinceDate.setText("Dibuat tanggal" + selectedDate);

                    dialog.dismiss();
                }
            }
        });
    }

    private void showDatePickerDialog(final EditText dateEditText) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                dateEditText.setText(selectedDate);
            }
        }, year, month, day);

        datePickerDialog.show();
    }

    private class FetchProvincesTask extends AsyncTask<Void, Void, List<String>> {
        @Override
        protected List<String> doInBackground(Void... voids) {
            List<String> provincesList = new ArrayList<>();
            provincesList.add("Pilih Provinsi");

            try {
                Koneksi koneksi = new Koneksi();
                URL url = new URL(koneksi.connProvince());
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
                JSONArray jsonArray = jsonResponse.getJSONArray("provinces");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject provinceObject = jsonArray.getJSONObject(i);
                    String provinceName = provinceObject.getString("name");
                    int provinceId = provinceObject.getInt("id");
                    provincesList.add(provinceName);
                    provinceIdMap.put(provinceName, provinceId); // Map province name to its ID
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return provincesList;
        }

        @Override
        protected void onPostExecute(List<String> provincesList) {
            ArrayAdapter<String> profAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, provincesList);
            profAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            provinceSpinner.setAdapter(profAdapter);
        }
    }

    private class FetchCitiesTask extends AsyncTask<Void, Void, List<String>> {
        private int provinceId;

        public FetchCitiesTask(int provinceId) {
            this.provinceId = provinceId;
        }

        @Override
        protected List<String> doInBackground(Void... voids) {
            List<String> citiesList = new ArrayList<>();
            citiesList.add("Pilih Kabupaten/Kota");

            try {
                Koneksi koneksi = new Koneksi();
                URL url = new URL(koneksi.connCities(provinceId));
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
                JSONArray jsonArray = jsonResponse.getJSONArray("cities");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject cityObject = jsonArray.getJSONObject(i);
                    String cityName = cityObject.getString("name");
                    int cityId = cityObject.getInt("id");
                    citiesList.add(cityName);
                    cityIdMap.put(cityName, cityId); // Map city name to its ID
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return citiesList;
        }

        @Override
        protected void onPostExecute(List<String> citiesList) {
            ArrayAdapter<String> kabAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, citiesList);
            kabAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            citySpinner.setAdapter(kabAdapter);
        }
    }
}
