package com.example.corruptionperceptionindex.src.register;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.corruptionperceptionindex.R;

import java.util.ArrayList;
import java.util.List;

public class thirdRegister extends Fragment {

    Spinner provinsiSpinner, kabupatenkotaSpiner, kacamatanSpiner, kelurahanSpinner;
    ImageView profileLayout;
    Button saveButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_third_register, container, false);

        profileLayout = view.findViewById(R.id.imageLayout);
        profileLayout.setImageResource(R.mipmap.profile3);

        provinsiSpinner = view.findViewById(R.id.provinsiSpiner);
        kabupatenkotaSpiner = view.findViewById(R.id.kabupatenkotaSpinner);
        kacamatanSpiner = view.findViewById(R.id.kecamatanSpiner);
        kelurahanSpinner = view.findViewById(R.id.kelurahandesaSpinner);

        saveButton = view.findViewById(R.id.btn_save);

        List<String> provinisiList = new ArrayList<>();
        provinisiList.add("Pilih Provinsi");
        ;
        provinisiList.add("DLL");

        List<String> KabupatenList = new ArrayList<>();
        KabupatenList.add("Pilih Kabupaten/Kota");
        KabupatenList.add("DLL");

        List<String> KacamatanList = new ArrayList<>();
        KacamatanList.add("Pilih Kacamatan");
        KacamatanList.add("DLL");

        List<String> KelurahanList = new ArrayList<>();
        KelurahanList.add("Pilih Kelurahan/Desa");
        KelurahanList.add("DLL");

        if (!provinsiSpinner.equals("Pilih Provinsi") &&
                !kabupatenkotaSpiner.equals("Pilih Kabupaten/Kota") &&
                !kacamatanSpiner.equals("Pilih Kacamatan") &&
                !kelurahanSpinner.equals("Pilih Kelurahan/Desa")) {
            saveButton.setEnabled(false);
        } else {
            saveButton.setEnabled(true);
        }

        ArrayAdapter<String> profAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, provinisiList);
        profAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> kabAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, KabupatenList);
        kabAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> kacAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, KacamatanList);
        kacAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> kelAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, KelurahanList);
        kelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        provinsiSpinner.setAdapter(profAdapter);
        kabupatenkotaSpiner.setAdapter(kabAdapter);
        kacamatanSpiner.setAdapter(kacAdapter);
        kelurahanSpinner.setAdapter(kelAdapter);

        return view;
    }
}