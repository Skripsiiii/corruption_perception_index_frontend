package com.example.corruptionperceptionindex.src.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.corruptionperceptionindex.R;
import com.example.corruptionperceptionindex.src.adapter.persepsiAdapter;

public class persepsiFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_persepsi, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.listView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Responses", Context.MODE_PRIVATE);
        String[] responses = {
                sharedPreferences.getString("question1_response", ""),
                sharedPreferences.getString("question2_response", ""),
                sharedPreferences.getString("question3_response", ""),
                sharedPreferences.getString("question4_response", ""),
                sharedPreferences.getString("question5_response", ""),
                sharedPreferences.getString("question6_response", ""),
                sharedPreferences.getString("question7_response", ""),
                sharedPreferences.getString("question8_response", "")
        };

        String[] listQuestion = {
                "Penilaian Anda terhadap upaya menghentikan korupsi dan suap di sektor swasta melalui KONVENSI INTERNASIONAL?",
                "Penilaian Anda terhadap upaya menghentikan korupsi dan suap di sektor swasta melalui KEBIJAKAN ANTI SUAP NASIONAL?",
                "Penilaian Anda terhadap upaya menghentikan korupsi dan suap di sektor swasta melalui JURNALISME INVESTIGATIF",
                "Penilaian Anda terhadap upaya menghentikan korupsi dan suap di sektor swasta melalui FORUM MULTIPIHAK DAN AKSI BERSAMA MELAWAN SUAP",
                "Penilaian Anda terhadap upaya menghentikan korupsi dan suap di sektor swasta melalui UJI KEPATUTAN",
                "Penilaian Anda terhadap upaya menghentikan korupsi dan suap di sektor swasta dengan cara MEMASUKKAN KORUPSI DALAM STRATEGI MANAJEMEN RISIKO PERUSAHAAN",
                "Penilaian Anda terhadap upaya menghentikan korupsi dan suap di sektor swasta dengan cara melalui KURIKULUM SEKOLAH/PERGURUAN TINGGI",
                "Penilaian Anda terhadap upaya menghentikan korupsi dan suap di sektor swasta dengan cara melalui PEMBERIAN SANKSI SOSIAL"
        };

        persepsiAdapter adapter = new persepsiAdapter(listQuestion, responses);

        recyclerView.setAdapter(adapter);

        return rootView;
    }
}
