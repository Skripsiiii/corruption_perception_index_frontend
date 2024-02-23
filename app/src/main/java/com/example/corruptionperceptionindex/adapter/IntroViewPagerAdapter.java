package com.example.corruptionperceptionindex.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.corruptionperceptionindex.fragments.IntroFragment;
import com.example.corruptionperceptionindex.items.ScreenItem;

import java.util.List;

public class IntroViewPagerAdapter extends FragmentStateAdapter {
    private Context mContext;
    private List<ScreenItem> mListScreen;
    public IntroViewPagerAdapter(FragmentActivity fragmentActivity, List<ScreenItem> mListScreen) {
        super(fragmentActivity);
        this.mContext = fragmentActivity;
        this.mListScreen = mListScreen;
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return IntroFragment.newInstance(mListScreen.get(position));
    }

    @Override
    public int getItemCount() {
        return mListScreen.size();
    }
}
