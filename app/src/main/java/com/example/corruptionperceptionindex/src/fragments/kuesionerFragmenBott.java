package com.example.corruptionperceptionindex.src.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.corruptionperceptionindex.R;
import com.example.corruptionperceptionindex.src.ViewPager.ViewPagerAdapter;
import com.example.corruptionperceptionindex.src.fragmentsKuesioner.dimenssionFirst;
import com.example.corruptionperceptionindex.src.screens.kuesioner.dimenssionQuestion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class kuesionerFragmenBott extends Fragment {

    private ViewPager2 viewPager;
    private Handler slideHandler = new Handler(Looper.getMainLooper());
    private final long SLIDE_DELAY = 3000;
    private int direction = 1;
    Button mulaiKuesioner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kuesioner_fragmen_bott, container, false);

        mulaiKuesioner = view.findViewById(R.id.btn_mulai);

        viewPager = view.findViewById(R.id.viewPager);
        List<String> data = Arrays.asList(
                "Klik pada jawaban yang paling sesuai dari 1 - 10.",
                "Kemajuan anda akan disimpan secara otomatis.",
                "Dapatkan indeks korupsi tepat setelah anda selesai."
        );
        List<Integer> imageList = Arrays.asList(
                R.mipmap.slidefirst,
                R.mipmap.slidesecond,
                R.mipmap.slidethird
        );

        ViewPagerAdapter adapter = new ViewPagerAdapter(data, imageList);
        viewPager.setAdapter(adapter);
        setupAutoSlide();

        mulaiKuesioner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startQuestionaire = new Intent(getActivity(), dimenssionQuestion.class);
                startActivity(startQuestionaire);
            }
        });


        return view;
    }

    private void setupAutoSlide() {
        slideHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int currentItem = viewPager.getCurrentItem();
                int itemCount = viewPager.getAdapter().getItemCount();
                // cek slide maju ato mundur
                if (currentItem == itemCount - 1 && direction == 1) {
                    // intung slide dari belakang ke depan kalo udah ke 3
                    direction = -1;
                } else if (currentItem == 0 && direction == -1) {
                    // itung slide dari depan ke belakang kalo dah pertama
                    direction = 1;
                }
                // untuk geser gesernya
                viewPager.setCurrentItem(currentItem + direction, true);
                // Setup ulang otomatis slide
                setupAutoSlide();
            }
        }, SLIDE_DELAY);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Hentikan otomatis slide saat fragment di-destroy
        slideHandler.removeCallbacksAndMessages(null);
    }
}
