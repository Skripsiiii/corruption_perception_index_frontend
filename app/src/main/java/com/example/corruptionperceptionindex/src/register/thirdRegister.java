package com.example.corruptionperceptionindex.src.register;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.corruptionperceptionindex.R;
import com.example.corruptionperceptionindex.src.connection.Koneksi;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class thirdRegister extends Fragment {

    Spinner provinsiSpinner, kabupatenkotaSpiner, kacamatanSpiner, kelurahanSpinner;
    ImageView profileLayout;
    Button saveButton;
    TextView idUser;
    EditText kacamatanEdt, kelurahanEdt;

    private HashMap<String, Integer> provinceIdMap = new HashMap<>();
    private HashMap<String, Integer> cityIdMap = new HashMap<>();
    private int userId;
    private int selectedCityId;
    private String token;
    com.google.android.material.textfield.TextInputLayout provinsiLayout, cityLayout, kacamatanLayout, kotaTVLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_third_register, container, false);

        idUser = view.findViewById(R.id.userid);
        idUser.setVisibility(View.GONE);

        profileLayout = view.findViewById(R.id.imageLayout);
        profileLayout.setImageResource(R.mipmap.profile3);

        kacamatanEdt = view.findViewById(R.id.kacamatanEditText);
        kelurahanEdt = view.findViewById(R.id.kelurahandesaEditText);

        provinsiSpinner = view.findViewById(R.id.provinsiSpiner);
        kabupatenkotaSpiner = view.findViewById(R.id.kabupatenkotaSpinner);

        provinsiLayout = view.findViewById(R.id.provinsiLayout);
        cityLayout = view.findViewById(R.id.cityLayout);
        kacamatanLayout = view.findViewById(R.id.kacamatanLayout);
        kotaTVLayout = view.findViewById(R.id.kotaLayout);

        saveButton = view.findViewById(R.id.btn_save);

        SharedPreferences prefs = requireActivity().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        token = prefs.getString("token", null);
        if (token != null) {
            Log.d("token", token);
        } else {
            Log.d("token", "Token is null");
            Toast.makeText(getContext(), "Anda belum melakukan Login", Toast.LENGTH_SHORT).show();
        }

        // Fetch provinces data
        new FetchProvincesTask().execute();

        provinsiSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    String selectedProvinceName = (String) provinsiSpinner.getSelectedItem();
                    int selectedProvinceId = provinceIdMap.get(selectedProvinceName);
                    new FetchCitiesTask(selectedProvinceId).execute();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        kabupatenkotaSpiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    String selectedCityName = (String) kabupatenkotaSpiner.getSelectedItem();
                    selectedCityId = cityIdMap.get(selectedCityName);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        saveButton.setOnClickListener(v -> {
            if (validateFieldsAndRegister()) {
                if (selectedCityId != 0 && token != null) {
                    new AddDomicileTask().execute(selectedCityId);

                    String selectedProvinsi = (String) provinsiSpinner.getSelectedItem();
                    String selectedKabupaten = (String) kabupatenkotaSpiner.getSelectedItem();
                    saveData(selectedProvinsi, selectedKabupaten);

                    ViewPager2 viewPager = requireActivity().findViewById(R.id.viewPager);
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
                } else {
                    Toast.makeText(getContext(), "Anda belum melakukan Login", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Get the user ID from the arguments
//        if (getArguments() != null) {
//            userId = getArguments().getInt("USER_ID", 0);
//            idUser.setText(String.valueOf(userId)); // Display the user ID
//        }

        return view;
    }

    private void initializeSpinners() {
        List<String> kacamatanList = new ArrayList<>();
        kacamatanList.add("Pilih Kacamatan");
        kacamatanList.add("DLL");

        List<String> kelurahanList = new ArrayList<>();
        kelurahanList.add("Pilih Kelurahan/Desa");
        kelurahanList.add("DLL");

        ArrayAdapter<String> kacAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, kacamatanList);
        kacAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> kelAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, kelurahanList);
        kelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        kacamatanSpiner.setAdapter(kacAdapter);
        kelurahanSpinner.setAdapter(kelAdapter);
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
            provinsiSpinner.setAdapter(profAdapter);
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
            kabupatenkotaSpiner.setAdapter(kabAdapter);
        }
    }

    private class AddDomicileTask extends AsyncTask<Integer, Void, String> {
        @Override
        protected String doInBackground(Integer... params) {
//            int userId = params[0];
            int cityId = params[0];

            HttpURLConnection urlConnection = null;
            try {
                Koneksi koneksi = new Koneksi();
                URL url = new URL(koneksi.connDomicili());
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json; utf-8");
                urlConnection.setRequestProperty("Accept", "application/json");
                urlConnection.setRequestProperty("Authorization", "Bearer " + token);
                urlConnection.setDoOutput(true);

                // Create the JSON payload
                JSONObject jsonParam = new JSONObject();
//                jsonParam.put("userId", userId);
                jsonParam.put("cityId", cityId);
                Log.d("AddDomicileTask", "cityId: " + cityId);

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
                    return jsonResponse.getString("message");
                } else {
                    // Log the error response
                    BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getErrorStream()));
                    String inputLine;
                    StringBuilder response = new StringBuilder();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    return "Berhasil menambahkan domisili";
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "Berhasil menambahkan domisili";
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getContext(), result, Toast.LENGTH_SHORT).show();
        }
    }

    private void saveData(String provinsi, String kabupaten) {
        SharedPreferences prefs = requireActivity().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("selectprovinsi", provinsi);
        editor.putString("selectkabupaten", kabupaten);
        editor.apply();
    }

    private boolean validateFieldsAndRegister() {
        provinsiLayout.setError(null);
        cityLayout.setError(null);
        kacamatanLayout.setError(null);
        kotaTVLayout.setError(null);

        // Check for null references before accessing the spinners and EditTexts
        if (provinsiSpinner == null || kabupatenkotaSpiner == null || kacamatanEdt == null || kelurahanEdt == null) {
            Toast.makeText(getContext(), "Please make sure all fields are properly initialized.", Toast.LENGTH_SHORT).show();
            return false;
        }

        String selectedProvinsi = provinsiSpinner.getSelectedItem() != null ? provinsiSpinner.getSelectedItem().toString() : "";
        String selectedKabupatenKota = kabupatenkotaSpiner.getSelectedItem() != null ? kabupatenkotaSpiner.getSelectedItem().toString() : "";
        String kacamatanText = this.kacamatanEdt.getText().toString();
        String kelurahanText = this.kelurahanEdt.getText().toString();

        boolean isValid = true;

        if (selectedProvinsi.equals("Pilih Provinsi")) {
            provinsiLayout.setError("Pilih Provinsi anda");
            isValid = false;
        }

        if (selectedKabupatenKota.equals("Pilih Kabupaten/Kota")) {
            cityLayout.setError("Pilih Kabupaten/Kota anda");
            isValid = false;
        }

        if (TextUtils.isEmpty(kacamatanText)) {
            kacamatanLayout.setError("Masukan Kacamatan anda");
        }

        if (TextUtils.isEmpty(kelurahanText)) {
            kotaTVLayout.setError("Masukan Kelurahan/Desa anda");
        }

        return isValid;
    }
}
