package com.example.corruptionperceptionindex.src.fragmentsData;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.corruptionperceptionindex.R;
import com.example.corruptionperceptionindex.src.adapter.dataProvinsiSecondAdapter;

import java.util.ArrayList;
import java.util.List;

public class dataProvinsiSecondFragment extends Fragment {

    TextView namaProvinsiTextView;
    RecyclerView recyclerView;
    private List<String> kabupatenKotaList;
    private String provinsiName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_data_provinsi_second_fragment, container, false);

        namaProvinsiTextView = view.findViewById(R.id.namaProvinsi);

        Bundle args = getArguments();
        if (args != null) {
            provinsiName = args.getString("provinsiName");
            namaProvinsiTextView.setText(provinsiName);
        }

        kabupatenKotaList = new ArrayList<>();
        kabupatenKotaList.add("Banda Aceh");
        kabupatenKotaList.add("Sabang");
        kabupatenKotaList.add("Langsa");
        kabupatenKotaList.add("Lhokseumawe");
        kabupatenKotaList.add("Subulussalam");

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        dataProvinsiSecondAdapter adapter = new dataProvinsiSecondAdapter(kabupatenKotaList, new dataProvinsiSecondAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String kabupatenKota) {
                // Load the third fragment and pass the data
                dataProvinsiThirdFragment thirdFragment = new dataProvinsiThirdFragment();
                Bundle bundle = new Bundle();
                bundle.putString("provinsiName", provinsiName);
                bundle.putString("kabupatenKota", kabupatenKota);
                thirdFragment.setArguments(bundle);

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, thirdFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        recyclerView.setAdapter(adapter);

        return view;
    }
}
