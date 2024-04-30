package com.example.corruptionperceptionindex.src.fragments;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.corruptionperceptionindex.R;
import androidx.viewpager2.widget.ViewPager2;
import com.example.corruptionperceptionindex.src.adapter.RegistrationPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class RegisterFragment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_fragment);

        ViewPager2 viewPager = findViewById(R.id.viewPager);
        TabLayout tabLayout = findViewById(R.id.tabLayout);

        RegistrationPagerAdapter adapter = new RegistrationPagerAdapter(getSupportFragmentManager(), getLifecycle());
        viewPager.setAdapter(adapter);

        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(
                tabLayout,
                viewPager,
                (tab, position) -> {
                    switch (position) {
                        case 0:
                            tab.setText("1");
                            break;
                        case 1:
                            tab.setText("2");
                            break;
                        case 2:
                            tab.setText("3");
                            break;
                        case 3:
                            tab.setText("4");
                            break;
                        case 4:
                            tab.setText("5");
                            break;
                        case 5:
                            tab.setText("6");
                            break;
                    }
                }
        );
        tabLayoutMediator.attach();
    }
}
