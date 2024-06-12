package com.example.corruptionperceptionindex.src.adapter;

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
    private String[] fragmentTitles = {"Daftar", "Melengkapi Profile", "Melengkapi Profile", "Persepsi Publik", "Persepsi Publik", "Persepsi Publik"};
    private int userId;

    public RegistrationPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, int userId) {
        super(fragmentManager, lifecycle);
        this.userId = userId;
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
        return fragmentTitles[position];
    }
}
