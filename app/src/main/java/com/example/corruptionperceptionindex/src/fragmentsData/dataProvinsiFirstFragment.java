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
import com.example.corruptionperceptionindex.src.connection.FetchProvinceDataTask;
import com.example.corruptionperceptionindex.src.connection.Koneksi;
import com.example.corruptionperceptionindex.src.model.ProvinceData;

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

        Koneksi koneksi = new Koneksi();
        String apiUrl = koneksi.connDataProvince();

        new FetchProvinceDataTask(getContext(), new FetchProvinceDataTask.OnProvinceDataFetchedListener() {
            @Override
            public void onProvinceDataFetched(List<ProvinceData> provinceData) {
                adapter = new dataProvinsiAdapter(provinceData);
                adapter.setOnItemClickListener(provinsi -> {
                    if (mListener != null) {
                        mListener.onProvinsiSelected(String.valueOf(provinsi));
                    }
                });
                recyclerView.setAdapter(adapter);
            }
        }).execute(apiUrl);

        return view;
    }
}
