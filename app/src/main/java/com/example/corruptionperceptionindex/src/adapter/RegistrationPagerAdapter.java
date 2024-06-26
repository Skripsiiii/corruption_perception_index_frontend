package com.example.corruptionperceptionindex.src.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.corruptionperceptionindex.src.register.fifthRegister;
import com.example.corruptionperceptionindex.src.register.firstRegister;
import com.example.corruptionperceptionindex.src.register.fourthActivity;
import com.example.corruptionperceptionindex.src.register.secondRegister;
import com.example.corruptionperceptionindex.src.register.sixthRegister;
import com.example.corruptionperceptionindex.src.register.thirdRegister;

public class RegistrationPagerAdapter extends FragmentStateAdapter {
    private static final int NUM_PAGES = 6;
    private String[] fragmentTitles = {"Daftar", "Melengkapi Profile", "Domisiliku", "Persepsi Publik terhadap", "Persepsi Publik terhadap", "Persepsi Publik terhadap"};
    private int userId;
    private Context context;

    public RegistrationPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, int userId, Context context) {
        super(fragmentManager, lifecycle);
        this.userId = userId;
        this.context = context;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Return the fragment for each position
        switch (position) {
            case 0:
                return new firstRegister();
            case 1:
                return new secondRegister();
            case 2:
                thirdRegister thirdRegisterFragment = new thirdRegister();
                Bundle bundle = new Bundle();
                bundle.putInt("USER_ID", userId);
                thirdRegisterFragment.setArguments(bundle);
                return thirdRegisterFragment;
            case 3:
                return new fourthActivity();
            case 4:
                return new fifthRegister();
            case 5:
                return new sixthRegister();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return NUM_PAGES; // Return the total number of pages
    }

    public String getFragmentTitle(int position) {
        if (position >= 3 && position <= 5) {
            SharedPreferences prefs = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
            String kabupaten = prefs.getString("selectkabupaten", "");
            return fragmentTitles[position] + " " + kabupaten;
        }
        return fragmentTitles[position];
    }
}
