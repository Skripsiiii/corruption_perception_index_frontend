package com.example.corruptionperceptionindex.src.fragments;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import androidx.viewpager2.widget.ViewPager2;
import com.example.corruptionperceptionindex.R;
import com.example.corruptionperceptionindex.src.adapter.RegistrationPagerAdapter;
import com.example.corruptionperceptionindex.src.screens.login.LoginActivity;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class RegisterFragment extends AppCompatActivity {
    TextView titleText, gotoLogin;
    private int userId;
    private int step;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_fragment);

        titleText = findViewById(R.id.title);
        gotoLogin = findViewById(R.id.LoginButton);

        gotoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginActivity = new Intent(RegisterFragment.this, LoginActivity.class);
                startActivity(loginActivity);
                finish();
            }
        });

        // Get the user ID and step from the Intent
        userId = getIntent().getIntExtra("USER_ID", 0);
        step = getIntent().getIntExtra("STEP", 0);

        ViewPager2 viewPager = findViewById(R.id.viewPager);
        TabLayout tabLayout = findViewById(R.id.tabLayout);

        RegistrationPagerAdapter adapter = new RegistrationPagerAdapter(getSupportFragmentManager(), getLifecycle(), userId);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            // Set the custom tab view
            View tabView = LayoutInflater.from(this).inflate(R.layout.tab_item, null);
            TextView tabTitle = tabView.findViewById(R.id.tabTitle);
            tabTitle.setText(String.valueOf(position + 1));
            tab.setCustomView(tabView);
        }).attach();

        viewPager.setCurrentItem(step, false);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                titleText.setText(adapter.getFragmentTitle(position)); // Mengatur judul sesuai posisi
                Log.d("Title", "Judul yang diatur: " + adapter.getFragmentTitle(position));

                // Enable or disable tabs based on the position
                for (int i = 0; i < tabLayout.getTabCount(); i++) {
                    TabLayout.Tab tab = tabLayout.getTabAt(i);
                    if (tab != null && tab.getCustomView() != null) {
                        TextView tabTitle = tab.getCustomView().findViewById(R.id.tabTitle);
                        if (position >= 3) { // Disable the first three tabs when position is 3 or more
                            tabLayout.getTabAt(i).view.setEnabled(i >= 3);
                            tabTitle.setTextColor(i < 3 ? getResources().getColor(R.color.gray) : getResources().getColor(android.R.color.black));
                        } else { // Enable all tabs otherwise
                            tabLayout.getTabAt(i).view.setEnabled(true);
                            tabTitle.setTextColor(getResources().getColor(android.R.color.black));
                        }
                    }
                }
            }
        });
    }
}
