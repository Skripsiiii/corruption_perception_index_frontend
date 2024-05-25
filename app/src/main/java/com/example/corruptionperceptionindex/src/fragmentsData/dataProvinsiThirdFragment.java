package com.example.corruptionperceptionindex.src.fragmentsData;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.corruptionperceptionindex.R;
import com.example.corruptionperceptionindex.src.adapter.dataProvinsiThirdAdapter;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.corruptionperceptionindex.R;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.corruptionperceptionindex.R;
import com.example.corruptionperceptionindex.src.adapter.dataProvinsiThirdAdapter;

import java.util.ArrayList;
import java.util.List;

public class dataProvinsiThirdFragment extends Fragment {

    TextView namaProvinsiTextView, namaKabupatenKotaTextView;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_data_provinsi_third_fragment, container, false);

        namaProvinsiTextView = view.findViewById(R.id.namaProvinsi);
        namaKabupatenKotaTextView = view.findViewById(R.id.namakabupatenkota);
        recyclerView = view.findViewById(R.id.recycler_view);

        Bundle args = getArguments();
        if (args != null) {
            String provinsiName = args.getString("provinsiName");
            String kabupatenKota = args.getString("kabupatenKota");

            namaProvinsiTextView.setText(provinsiName);
            namaKabupatenKotaTextView.setText(kabupatenKota);
        }

        //data dummy nanti APINYA nyusul

        List<String> dimensiList = new ArrayList<>();
        dimensiList.add("Dimensi Persepsi Anda tentang Sistem Integritas Lokal di Lingkungan Kabupaten/Kota");
        dimensiList.add("Dimensi Persepsi Anda tentang Integritas Layanan Dilihat Dari Ketersediaan Berbagai Prosedur Dalam Melayani Masyarakat di Lingkungan Kabupaten/Kota");
        dimensiList.add("Dimensi Penilaian Anda tentang Implementasi Berbagai Prosedur Dalam Melayani Masyarakat di Lingkungan Kabupaten/Kota");
        dimensiList.add("Dimensi Persepsi Anda terhadap Perilaku Korup Aparat di Lingkungan Kabupaten/Kota");
        dimensiList.add("Dimensi Persepsi Anda tentang Kemampuan Daya Saing Kabupaten/Kota");
        dimensiList.add("Dimensi Persepsi Anda terhadap Pengetahuan Aparat di Lingkungan Kabupaten/Kota tentang Strategi Pencegahan dan Pemberantasan Korupsi");

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        dataProvinsiThirdAdapter adapter = new dataProvinsiThirdAdapter(dimensiList);
        recyclerView.setAdapter(adapter);

        adapter.setOnNextButtonClickListener(new dataProvinsiThirdAdapter.OnNextButtonClickListener() {
            @Override
            public void onNextButtonClick() {
                // buat ambil datanya bang
                String provinsiName = args.getString("provinsiName");
                String kabupatenKota = args.getString("kabupatenKota");
                String selectedDimensi = adapter.getSelectedDimensi();

                dataProvinsiFourthFragment fourthFragment = new dataProvinsiFourthFragment();
                Bundle bundle = new Bundle();
                bundle.putString("provinsiName", provinsiName);
                bundle.putString("kabupatenKota", kabupatenKota);
                bundle.putString("selectedDimensi", selectedDimensi);
                fourthFragment.setArguments(bundle);

                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, fourthFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }
}

