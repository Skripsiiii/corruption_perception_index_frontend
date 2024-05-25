package com.example.corruptionperceptionindex.src.fragmentsData;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.corruptionperceptionindex.R;
import com.example.corruptionperceptionindex.src.adapter.dataProvinsiAdapter;

import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class dataProvinsiFirstFragment extends Fragment {

    private RecyclerView recyclerView;
    private dataProvinsiAdapter adapter;
    private OnProvinsiSelectedListener mListener;

    public interface OnProvinsiSelectedListener {
        void onProvinsiSelected(String provinsi);
    }

    public void setOnProvinsiSelectedListener(OnProvinsiSelectedListener listener) {
        this.mListener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_data_provinsi, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Bundle args = getArguments();
        int pageNumber = 1;
        if (args != null) {
            pageNumber = args.getInt("pageNumber", 1);
        }

        List<String> fullProvinsiList = generateProvinsiList();
        int itemsPerPage = 10;
        int startIndex = (pageNumber - 1) * itemsPerPage;
        int endIndex = Math.min(startIndex + itemsPerPage, fullProvinsiList.size());
        List<String> currentProvinsiList = fullProvinsiList.subList(startIndex, endIndex);

        adapter = new dataProvinsiAdapter(currentProvinsiList);
        adapter.setOnItemClickListener(new dataProvinsiAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String provinsi) {
                if (mListener != null) {
                    mListener.onProvinsiSelected(provinsi);
                }
            }
        });
        recyclerView.setAdapter(adapter);

        return view;
    }

    private List<String> generateProvinsiList() {
        List<String> provinsiList = new ArrayList<>();
        provinsiList.add("Aceh");
        provinsiList.add("Bali");
        provinsiList.add("Banten");
        provinsiList.add("Bengkulu");
        provinsiList.add("Gorontalo");
        provinsiList.add("Jakarta");
        provinsiList.add("Jambi");
        provinsiList.add("Jawa Barat");
        provinsiList.add("Jawa Tengah");
        provinsiList.add("Jawa Timur");
        provinsiList.add("Kalimantan Barat");
        provinsiList.add("Kalimantan Selatan");
        provinsiList.add("Kalimantan Tengah");
        provinsiList.add("Kalimantan Timur");
        provinsiList.add("Kalimantan Utara");
        provinsiList.add("Kepulauan Bangka Belitung");
        provinsiList.add("Kepulauan Riau");
        provinsiList.add("Lampung");
        provinsiList.add("Maluku");
        provinsiList.add("Maluku Utara");
        provinsiList.add("Nusa Tenggara Barat");
        provinsiList.add("Nusa Tenggara Timur");
        provinsiList.add("Papua");
        provinsiList.add("Papua Barat");
        provinsiList.add("Papua Tengah");
        provinsiList.add("Papua Pegunungan");
        provinsiList.add("Papua Selatan");
        provinsiList.add("Papua Barat Daya");
        provinsiList.add("Riau");
        provinsiList.add("Sulawesi Barat");
        provinsiList.add("Sulawesi Selatan");
        provinsiList.add("Sulawesi Tengah");
        provinsiList.add("Sulawesi Tenggara");
        provinsiList.add("Sulawesi Utara");
        provinsiList.add("Sumatera Barat");
        provinsiList.add("Sumatera Selatan");
        provinsiList.add("Sumatera Utara");
        provinsiList.add("Yogyakarta");

        return provinsiList;
    }
}
