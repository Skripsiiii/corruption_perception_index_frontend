package com.example.corruptionperceptionindex.src.screens.intro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.corruptionperceptionindex.R;
import com.example.corruptionperceptionindex.src.adapter.IntroViewPagerAdapter;
import com.example.corruptionperceptionindex.src.fragments.RegisterFragment;
import com.example.corruptionperceptionindex.src.items.IntroItem;
import com.example.corruptionperceptionindex.src.register.firstRegister;
import com.example.corruptionperceptionindex.src.screens.login.LoginActivity;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AppCompatActivity {

    private ViewPager2 screenPager;
    private LinearLayout dotLayout;
    private RelativeLayout nextButton;
    private TextView participateNowTextView;
    IntroViewPagerAdapter introViewPagerAdapter;
    private List<IntroItem> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        screenPager = findViewById(R.id.screen_viewpager);
        dotLayout = findViewById(R.id.dot_layout);
        nextButton = findViewById(R.id.next_button_layout);

        mList = new ArrayList<>();
        mList.add(new IntroItem("What is CPI?", "Corruption Perception Index, abbreviated as CPI, is an index of corruption on regional basis.", R.mipmap.pie_chart));
        mList.add(new IntroItem("CPI Score", "CPI is represented on a scale of 0-100, where 0 indicates a region is highly corrupt, and 100 is very clean.", R.mipmap.earth));
        mList.add(new IntroItem("Let’s Eradicate Corruption", "Your participation filling the questionnaire of corruption perspective is a precious contribution for corruption eradication in Indonesia.", R.mipmap.pie_chart));

        introViewPagerAdapter = new IntroViewPagerAdapter(this, mList);
        screenPager.setAdapter(introViewPagerAdapter);

        setDotColor(0);

        screenPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setDotColor(position);
                if (position == mList.size() - 1) {
                    changeNextButtonLayout(true);
                    nextButton.setOnClickListener(v -> startActivity(new Intent(IntroActivity.this, RegisterFragment.class  )));
                } else {
                    changeNextButtonLayout(false);
                }
            }
        });

        nextButton.setOnClickListener(v -> {
            int nextIndex = screenPager.getCurrentItem() + 1;
            if (nextIndex < mList.size()) {
                screenPager.setCurrentItem(nextIndex);
            } else {
            }
        });
    }
    private void changeNextButtonLayout(boolean isLastPage) {
        nextButton.setBackgroundResource(isLastPage ? R.drawable.circular_button_background : R.drawable.circular_bg);

        ViewGroup.LayoutParams layoutParams = nextButton.getLayoutParams();
        layoutParams.width = getResources().getDimensionPixelSize(isLastPage ? R.dimen.new_button_width : R.dimen.old_button_width);
        nextButton.setLayoutParams(layoutParams);

        ImageView nextButtonImageView = nextButton.findViewById(R.id.next_button);
        nextButtonImageView.setVisibility(isLastPage ? View.GONE : View.VISIBLE);

        if (isLastPage) {
            participateNowTextView = new TextView(this);
            participateNowTextView.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            participateNowTextView.setText("Participate Now");
            participateNowTextView.setTextColor(getResources().getColor(R.color.white));
            nextButton.addView(participateNowTextView);
        } else {
            // Remove "Participate Now" TextView if it exists
            if (participateNowTextView != null) {
                nextButton.removeView(participateNowTextView);
                participateNowTextView = null;
            }
        }
    }

    private void setDotColor(int position) {
        for (int i = 0; i < dotLayout.getChildCount(); i++) {
            RelativeLayout dot = (RelativeLayout) dotLayout.getChildAt(i);
            if (i == position) {
                dotLayout.getChildAt(i).setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.black)));
            } else {
                dotLayout.getChildAt(i).setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.secondary_500)));
            }
        }
    }
}

