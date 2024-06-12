package com.example.corruptionperceptionindex.src.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.corruptionperceptionindex.R;

import java.util.Arrays;
import java.util.List;

public class profileFragment extends Fragment {

    EditText namaEdt, emailEdt, DoBEdt;
    Spinner genderSpinner, pendidikanSpinner, pekerjaanSpinner;
    TextView namaTv, emailTv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        namaEdt = view.findViewById(R.id.namaEditText);
        emailEdt = view.findViewById(R.id.emailEditText);
        DoBEdt = view.findViewById(R.id.DoBText);
        genderSpinner = view.findViewById(R.id.genderSpinner);
        pendidikanSpinner = view.findViewById(R.id.pendidikanSpinner);
        pekerjaanSpinner = view.findViewById(R.id.pekerjaanSpinner);
        namaTv = view.findViewById(R.id.nametxt);
        emailTv = view.findViewById(R.id.emailTxt);

        // Initialize Spinners with dummy data for demonstration purposes
        setupSpinners();

        // Load saved data
        loadSavedData();

        return view;
    }

    private void setupSpinners() {
        // Setup gender spinner
        List<String> genderList = Arrays.asList("Pilih Jenis Kelamin", "Laki-laki", "Perempuan");
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, genderList);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(genderAdapter);

        // Setup education spinner
        List<String> educationList = Arrays.asList("Pilih Pendidikan", "B3B", "SD atau yang sederajat", "SMP atau yang sederajat", "SMA\\/SMK atau yang sederajat", "Diploma", "S1", "S2", "S3");
        ArrayAdapter<String> educationAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, educationList);
        educationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pendidikanSpinner.setAdapter(educationAdapter);

        // Setup occupation spinner
        List<String> occupationList = Arrays.asList(
                "Legislatif / eksekutif / Pejabat",
                "Anggota DPR",
                "Anggota DPRD Provinsi",
                "Anggota DPRD Kabupaten/Kota",
                "Anggota BPD",
                "Anggota BPK",
                "Pengacara",
                "Notaris",
                "Perangkat Desa",
                "Kepala Desa",
                "Guru",
                "Dosen",
                "Wiraswasta",
                "Anggota TNI dan Polri",
                "PNS/ASN/Aparat Pemda",
                "BUMN/BUMD",
                "Manajer",
                "Tenaga Profesional",
                "Teknisi dan Asisten Tenaga Profesional",
                "Tenaga Tata Usaha",
                "Tenaga Usaha Pertanian dan Peternakan",
                "Tenaga Usaha Jasa dan Tenaga Usaha Penjualan di Toko dan Pasar",
                "Tenaga Pengolahan dan Kerajinan",
                "Operator dan Perakit Mesin",
                "Pekerja Kasar, Tenaga Kebersihan, dan Tenaga Lepas",
                "LSM",
                "Rohaniawan/wati",
                "Pensiunan",
                "Lainnya"
        );
        ArrayAdapter<String> occupationAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, occupationList);
        occupationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pekerjaanSpinner.setAdapter(occupationAdapter);
    }

    private void loadSavedData() {
        SharedPreferences prefs = requireActivity().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        String name = prefs.getString("name", "");
        String email = prefs.getString("email", "");
        String selectedGender = prefs.getString("selectedGender", "");
        String selectedDate = prefs.getString("selectedDate", "");
        String selectedEducation = prefs.getString("selectedEducation", "");
        String selectedOccupation = prefs.getString("selectedOccupation", "");

        namaEdt.setText(name);
        emailEdt.setText(email);
        DoBEdt.setText(selectedDate);
        namaTv.setText(name);
        emailTv.setText(email);

        // Set gender spinner
        setSpinnerSelection(genderSpinner, selectedGender);

        // Set education spinner
        setSpinnerSelection(pendidikanSpinner, selectedEducation);

        // Set occupation spinner
        setSpinnerSelection(pekerjaanSpinner, selectedOccupation);
    }

    private void setSpinnerSelection(Spinner spinner, String value) {
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) spinner.getAdapter();
        if (adapter != null) {
            int position = adapter.getPosition(value);
            if (position >= 0) {
                spinner.setSelection(position);
            }
        }
    }
}
