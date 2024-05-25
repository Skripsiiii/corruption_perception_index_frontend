package com.example.corruptionperceptionindex.src.register;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.corruptionperceptionindex.R;

import java.util.ArrayList;
import java.util.List;

public class secondRegister extends Fragment {
    Spinner genderSpinner, dateSpiner, educationSpiner, occupationSpinner;
    Button saveButton;
    ImageView profileLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_second_register, container, false);

        genderSpinner = view.findViewById(R.id.genderSpiner);
        dateSpiner = view.findViewById(R.id.dateSpinner);
        educationSpiner = view.findViewById(R.id.educationSpiner);
        occupationSpinner = view.findViewById(R.id.occupationSpinner);

        profileLayout = view.findViewById(R.id.imageLayout);
        profileLayout.setImageResource(R.mipmap.profile2);

        saveButton = view.findViewById(R.id.btn_login);
//         loginBtnEnableDisable();

        // data dummy buat gender
        List<String> genderList = new ArrayList<>();
        genderList.add("Pilih Jenis Kelamin");
        genderList.add("Laki-laki");
        genderList.add("Perempuan");

        // data dummy buat DoB
        List<String> DoBList = new ArrayList<>();
        DoBList.add("DD/MM/YY");
        DoBList.add("DLL");

        // data dummy buat pendidikan
        List<String> educationList = new ArrayList<>();
        educationList.add("Pilih Pendidikan");
        educationList.add("SMA");
        educationList.add("S1");
        educationList.add("S2");
        educationList.add("DLL");

        // data dummy buat pekerjaan
        List<String> occupationList = new ArrayList<>();
        occupationList.add("Pilih Pekerjaan");
        occupationList.add("Pendidikan");
        occupationList.add("Wiraswasta");
        occupationList.add("DLL");

//        if(genderList.equals("Pilih Jenis Kelamin") &&
//                DoBList.equals("DD/MM/YY") &&
//                educationList.equals("Pilih Pendidikan") &&
//                occupationList.equals("Pilih Pekerjaan")){
//
//            loginButton.setEnabled(true);
//        }else {
//            loginButton.setEnabled(false);
//        }

        if (!genderSpinner.equals("Pilih Jenis Kelamin") &&
                !dateSpiner.equals("DD/MM/YY") &&
                !educationSpiner.equals("Pilih Pendidikan") &&
                !occupationSpinner.equals("Pilih Pekerjaan")) {
            saveButton.setEnabled(false);
        } else {
            saveButton.setEnabled(true);
        }

        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, genderList);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> DoBAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, DoBList);
        DoBAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> educationAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, educationList);
        educationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> occupationAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, occupationList);
        occupationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        genderSpinner.setAdapter(genderAdapter);
        dateSpiner.setAdapter(DoBAdapter);
        educationSpiner.setAdapter(educationAdapter);
        occupationSpinner.setAdapter(occupationAdapter);

        return view;
    }

//    private void loginBtnEnableDisable() {
//        String jenisKelamin = genderSpinner.getSelectedItem().toString();
//        String tanggalLahir = dateSpiner.getSelectedItem().toString();
//        String pendidikan = educationSpiner.getSelectedItem().toString();
//        String pekerjaan = occupationSpinner.getSelectedItem().toString();
//
//        if (!jenisKelamin.equals("Pilih Jenis Kelamin") &&
//                !tanggalLahir.equals("DD/MM/YY") &&
//                !pendidikan.equals("Pilih Pendidikan") &&
//                !pekerjaan.equals("Pilih Pekerjaan")) {
//            loginButton.setEnabled(true);
//        } else {
//            loginButton.setEnabled(false);
//        }
//    }


}
