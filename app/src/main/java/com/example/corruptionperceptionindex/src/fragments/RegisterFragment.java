package com.example.corruptionperceptionindex.src.fragments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.corruptionperceptionindex.R;
import androidx.viewpager2.widget.ViewPager2;
import com.example.corruptionperceptionindex.src.adapter.RegistrationPagerAdapter;
import com.example.corruptionperceptionindex.src.screens.login.LoginActivity;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class RegisterFragment extends AppCompatActivity {
    TextView titleText, gotoLogin;

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

        ViewPager2 viewPager = findViewById(R.id.viewPager);
        TabLayout tabLayout = findViewById(R.id.tabLayout);

        RegistrationPagerAdapter adapter = new RegistrationPagerAdapter(getSupportFragmentManager(), getLifecycle());
        viewPager.setAdapter(adapter);

        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(
                tabLayout,
                viewPager,
                (tab, position) -> {
                    tab.setText(String.valueOf(position + 1)); // Mengatur teks tab sesuai posisi
                }
        );
        tabLayoutMediator.attach();

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                titleText.setText(adapter.getFragmentTitle(position)); // Mengatur judul sesuai posisi
                Log.d("Title", "Judul yang diatur: " + adapter.getFragmentTitle(position));
            }
        });

//        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(
//                tabLayout,
//                viewPager,
//                (tab, position) -> {
//                    switch (position) {
//                        case 0:
//                            tab.setText("1");
//                            titleText.setText("Daftar");
//                            break;
//                        case 1:
//                            tab.setText("2");
//                            titleText.setText("Melengkapi Profile");
//                            break;
//                        case 2:
//                            tab.setText("3");
//                            titleText.setText("Melengkapi Profile");
//                            break;
//                        case 3:
//                            tab.setText("4");
//                            titleText.setText("Persepsi Publik");
//                            break;
//                        case 4:
//                            tab.setText("5");
//                            titleText.setText("Persepsi Publik");
//                            break;
//                        case 5:
//                            tab.setText("6");
//                            titleText.setText("Persepsi Publik");
//                            break;
//                    }
//                }
//        );
//        tabLayoutMediator.attach();
//    }
    }
}