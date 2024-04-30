package com.example.corruptionperceptionindex.src.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.corruptionperceptionindex.src.register.firstRegister;
import com.example.corruptionperceptionindex.src.register.secondRegister;
import com.example.corruptionperceptionindex.src.register.thirdRegister;

public class RegistrationPagerAdapter extends FragmentStateAdapter {
    private static final int NUM_PAGES = 6; // Number of pages (fragments)

    public RegistrationPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
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
                return new thirdRegister();
            case 3:
                return new thirdRegister();
            case 4:
                return new thirdRegister();
            case 5:
                return new thirdRegister();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return NUM_PAGES; // Return the total number of pages
    }
}
