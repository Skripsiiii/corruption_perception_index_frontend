package com.example.corruptionperceptionindex.src.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;  // Tambahkan ini
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.corruptionperceptionindex.R;
import com.example.corruptionperceptionindex.src.ViewPager.ViewPagerAdapter;
import com.example.corruptionperceptionindex.src.connection.Koneksi;
import com.example.corruptionperceptionindex.src.register.secondRegister;
import com.example.corruptionperceptionindex.src.screens.kuesioner.dimenssionQuestion;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class kuesionerFragmenBott extends Fragment {

    Spinner provinsiSpinner, kabupatenkotaSpiner;
    private ViewPager2 viewPager;
    private Handler slideHandler = new Handler(Looper.getMainLooper());
    private final long SLIDE_DELAY = 3000;
    private int direction = 1;
    private HashMap<String, Integer> provinceIdMap = new HashMap<>();
    private HashMap<String, Integer> cityIdMap = new HashMap<>();
    Button mulaiKuesioner;
    EditText tahunEdt;
    private int selectedCityId;
    com.google.android.material.textfield.TextInputLayout provinsiLayout, kabupatenkotaLayout;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kuesioner_fragmen_bott, container, false);

        mulaiKuesioner = view.findViewById(R.id.btn_mulai);
        provinsiSpinner = view.findViewById(R.id.provinsiSpiner);
        kabupatenkotaSpiner = view.findViewById(R.id.kabupatenkotaSpinner);
        tahunEdt = view.findViewById(R.id.tahun);
        provinsiLayout = view.findViewById(R.id.provinsiLayout);
        kabupatenkotaLayout = view.findViewById(R.id.kabupatenkotaLayout);

        // Mengisi tahunEdt dengan tahun saat ini
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        tahunEdt.setText(String.valueOf(currentYear));

        new FetchProvincesTask().execute();

        viewPager = view.findViewById(R.id.viewPager);
        List<String> data = Arrays.asList(
                "Klik pada jawaban yang paling sesuai dari 1 - 10.",
                "Kemajuan anda akan disimpan secara otomatis.",
                "Dapatkan indeks korupsi tepat setelah anda selesai."
        );
        List<Integer> imageList = Arrays.asList(
                R.mipmap.slidefirst,
                R.mipmap.slidesecond,
                R.mipmap.slidethird
        );

        ViewPagerAdapter adapter = new ViewPagerAdapter(data, imageList);
        viewPager.setAdapter(adapter);
        setupAutoSlide();

        mulaiKuesioner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startQuestionaire = new Intent(getActivity(), dimenssionQuestion.class);

                String selectedProvinsi = (String) provinsiSpinner.getSelectedItem();
                String selectedKabupaten = (String) kabupatenkotaSpiner.getSelectedItem();
                String tahun = tahunEdt.getText().toString();
                saveData(selectedProvinsi, selectedKabupaten, tahun);

                // Log data yang akan disimpan
                Log.d("kuesionerFragmenBott", "Saving data - Provinsi: " + selectedProvinsi + ", Kabupaten: " + selectedKabupaten + ", Tahun: " + tahun);

                startActivity(startQuestionaire);
            }
        });

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

        return view;
    }

    private void setupAutoSlide() {
        slideHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int currentItem = viewPager.getCurrentItem();
                int itemCount = viewPager.getAdapter().getItemCount();
                // cek slide maju ato mundur
                if (currentItem == itemCount - 1 && direction == 1) {
                    // intung slide dari belakang ke depan kalo udah ke 3
                    direction = -1;
                } else if (currentItem == 0 && direction == -1) {
                    // itung slide dari depan ke belakang kalo dah pertama
                    direction = 1;
                }
                // untuk geser gesernya
                viewPager.setCurrentItem(currentItem + direction, true);
                // Setup ulang otomatis slide
                setupAutoSlide();
            }
        }, SLIDE_DELAY);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Hentikan otomatis slide saat fragment di-destroy
        slideHandler.removeCallbacksAndMessages(null);
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

    private void saveData(String provinsi, String kabupaten, String tahun) {
        SharedPreferences prefs = requireActivity().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("selectprovinsi", provinsi);
        editor.putString("selectkabupaten", kabupaten);
        editor.putString("tahun", tahun);
        editor.apply();
    }

//    private void validateFieldsAndRegister() {
//        provinsiLayout.setError(null);
//        kabupatenkotaLayout.setError(null);
//
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
//            new secondRegister.RegisterUserTask().execute();
//        }
//    }

}
