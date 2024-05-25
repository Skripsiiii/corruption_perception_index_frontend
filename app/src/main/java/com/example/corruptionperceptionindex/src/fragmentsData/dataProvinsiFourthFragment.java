package com.example.corruptionperceptionindex.src.fragmentsData;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.corruptionperceptionindex.R;
import com.example.corruptionperceptionindex.src.adapter.dataProvinsiFourthAdapter;
import com.example.corruptionperceptionindex.src.adapter.dataProvinsiThirdAdapter;

import java.util.ArrayList;
import java.util.List;

public class dataProvinsiFourthFragment extends Fragment {
    TextView namaProvinsiTextView, namaKabupatenKotaTextView, dimensiTextView;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_data_provinsi_fourth_fragment, container, false);

        namaProvinsiTextView = view.findViewById(R.id.namaProvinsi);
        namaKabupatenKotaTextView = view.findViewById(R.id.namakabupatenkota);
        dimensiTextView = view.findViewById(R.id.dimensiTxt);

        recyclerView = view.findViewById(R.id.recycler_view);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Bundle args = getArguments();
        if (args != null) {
            String provinsiName = args.getString("provinsiName");
            String kabupatenKota = args.getString("kabupatenKota");
            String dimensi = args.getString("selectedDimensi");

            namaProvinsiTextView.setText(provinsiName);
            namaKabupatenKotaTextView.setText(kabupatenKota);
            dimensiTextView.setText(dimensi);
        }
        setupRecyclerView();

        return view;
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        List<String> indikatorList = new ArrayList<>();
        indikatorList.add("Indikator Harapan Anda dalam Mencegah dan Memberantas Korupsi");
        indikatorList.add("Indikator Penilaian Anda di Kabupaten/Kota ini Terkait Kapasitas Mencegah dan Memberantas Korupsi");
        indikatorList.add("Indikator Penilaian Anda di Kabupaten/Kota ini Terkait Kualitas Tata Kelola Mencegah dan Membrantas Korupsi");
        dataProvinsiFourthAdapter adapter = new dataProvinsiFourthAdapter(indikatorList);
        recyclerView.setAdapter(adapter);
    }
}