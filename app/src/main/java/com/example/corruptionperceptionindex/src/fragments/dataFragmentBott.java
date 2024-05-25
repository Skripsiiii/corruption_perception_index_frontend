package com.example.corruptionperceptionindex.src.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.corruptionperceptionindex.R;
import com.example.corruptionperceptionindex.src.fragmentsData.dataProvinsiFirstFragment;
import com.example.corruptionperceptionindex.src.fragmentsData.dataProvinsiSecondFragment;

public class dataFragmentBott extends Fragment {

    private ImageView nextButton, prevButton;
    private TextView countPage;
    private int currentPage = 1;
    private boolean isSecondFragmentActive = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_data_bott, container, false);

        nextButton = view.findViewById(R.id.nextButton);
        prevButton = view.findViewById(R.id.prevButton);
        countPage = view.findViewById(R.id.countPage);

        countPage.setText(String.valueOf(currentPage));
        loadProvinsiFragment(currentPage);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isSecondFragmentActive && currentPage < 4) {
                    currentPage++;
                    countPage.setText(String.valueOf(currentPage));
                    loadProvinsiFragment(currentPage);
                }
            }
        });

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isSecondFragmentActive && currentPage > 1) {
                    currentPage--;
                    countPage.setText(String.valueOf(currentPage));
                    loadProvinsiFragment(currentPage);
                }
            }
        });

        return view;
    }



    private void loadProvinsiFragment(int pageNumber) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        dataProvinsiFirstFragment firstFragment = new dataProvinsiFirstFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("pageNumber", pageNumber);
        firstFragment.setArguments(bundle);

        firstFragment.setOnProvinsiSelectedListener(new dataProvinsiFirstFragment.OnProvinsiSelectedListener() {
            @Override
            public void onProvinsiSelected(String provinsi) {
                loadSecondFragment(provinsi);
            }
        });

        transaction.replace(R.id.fragment_container, firstFragment);
        transaction.commit();
        isSecondFragmentActive = false;
        updateButtonsState();
    }

//    private void loadSecondFragment(String provinsi) {
//        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
//        dataProvinsiSecondFragment secondFragment = new dataProvinsiSecondFragment();
//        Bundle args = new Bundle();
//        args.putString("provinsiName", provinsi);
//        secondFragment.setArguments(args);
//
//        transaction.replace(R.id.fragment_container, secondFragment);
//        transaction.addToBackStack(null);
//        transaction.commit();
//        isSecondFragmentActive = true;
//        updateButtonsState();
//    }

    private void loadSecondFragment(String provinsi) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        dataProvinsiSecondFragment secondFragment = new dataProvinsiSecondFragment();
        Bundle args = new Bundle();
        args.putString("provinsiName", provinsi);
        secondFragment.setArguments(args);

        transaction.replace(R.id.fragment_container, secondFragment);
        transaction.addToBackStack(null);
        transaction.commit();
        isSecondFragmentActive = true;
        updateButtonsState();
    }


    private void updateButtonsState() {
        if (isSecondFragmentActive) {
            nextButton.setEnabled(false);
            prevButton.setEnabled(false);
        } else {
            nextButton.setEnabled(currentPage < 4);
            prevButton.setEnabled(currentPage > 1);
        }
    }

}
