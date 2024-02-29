package com.example.corruptionperceptionindex.src.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.corruptionperceptionindex.src.fragments.IntroFragment;
import com.example.corruptionperceptionindex.src.items.IntroItem;

import java.util.List;

public class IntroViewPagerAdapter extends FragmentStateAdapter {
    private Context mContext;
    private List<IntroItem> mListIntro;
    public IntroViewPagerAdapter(FragmentActivity fragmentActivity, List<IntroItem> mListScreen) {
        super(fragmentActivity);
        this.mContext = fragmentActivity;
        this.mListIntro = mListScreen;
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return IntroFragment.newInstance(mListIntro.get(position));
    }

    @Override
    public int getItemCount() {
        return mListIntro.size();
    }
}
