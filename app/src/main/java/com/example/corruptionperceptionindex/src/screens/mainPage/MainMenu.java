package com.example.corruptionperceptionindex.src.screens.mainPage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Switch;
import android.widget.CompoundButton;

import com.example.corruptionperceptionindex.R;
import com.example.corruptionperceptionindex.src.fragments.changePassFragment;
import com.example.corruptionperceptionindex.src.fragments.dashboardFragmentBott;
import com.example.corruptionperceptionindex.src.fragments.dataFragmentBott;
import com.example.corruptionperceptionindex.src.fragments.domisiliFragment;
import com.example.corruptionperceptionindex.src.fragments.kuesionerFragmenBott;
import com.example.corruptionperceptionindex.src.fragments.kuesionerFragment;
import com.example.corruptionperceptionindex.src.fragments.mapFragmentBott;
import com.example.corruptionperceptionindex.src.fragments.persepsiFragment;
import com.example.corruptionperceptionindex.src.fragments.profileFragment;
import com.example.corruptionperceptionindex.src.screens.login.LoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainMenu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private profileFragment profileFragment;
    private kuesionerFragment kuesionerFragment;
    private mapFragmentBott mapFragment;
    private dataFragmentBott dataFragment;
    private BottomNavigationView bottomNavigationView;
    TextView title;
    private Switch darkModeSwitch;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        title = findViewById(R.id.titleTxt);
        drawerLayout = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Initialize the switch and shared preferences
        darkModeSwitch = navigationView.getHeaderView(0).findViewById(R.id.dark_mode_switch);
        sharedPreferences = getSharedPreferences("SharedPrefs", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        boolean isDarkModeOn = sharedPreferences.getBoolean("isDarkModeOn", false);

        if (isDarkModeOn) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            darkModeSwitch.setChecked(true);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            darkModeSwitch.setChecked(false);
        }

        darkModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    editor.putBoolean("isDarkModeOn", true);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    editor.putBoolean("isDarkModeOn", false);
                }
                editor.apply();
            }
        });

        // Initialize fragments
        profileFragment = new profileFragment();
        kuesionerFragment = new kuesionerFragment();
        mapFragment = new mapFragmentBott();
        dataFragment = new dataFragmentBott();
        dashboardFragmentBott dashboardFragment = new dashboardFragmentBott();

        // Initialize BottomNavigationView
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        // Display home fragment on activity creation
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, dashboardFragment).commit();

        title.setText("CPI");
        getSupportActionBar().setTitle("");

        loadSavedData();
    }

    public void selectBottomNavItem(int itemId) {
        if (bottomNavigationView != null) {
            bottomNavigationView.setSelectedItemId(itemId);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.myProfile) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new profileFragment()).commit();
            title.setText("Profil Saya");

        } else if (id == R.id.changePassword) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new changePassFragment()).commit();
            title.setText("Ganti Kata Sandi");
        } else if (id == R.id.domisili) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new domisiliFragment()).commit();
            title.setText("Domisili Saya");
        } else if (id == R.id.persepsi) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new persepsiFragment()).commit();
            title.setText("Persepsi Saya");
        } else if (id == R.id.kuesioner) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new kuesionerFragment()).commit();
            title.setText("Kuesioner Saya");
        } else if (id == R.id.logOut) {
            keluar();
            Toast.makeText(this, "Logout!", Toast.LENGTH_SHORT).show();
        }

        else if (id == R.id.mainBottom) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new dashboardFragmentBott()).commit();
            title.setText("CPI");
        } else if (id == R.id.kuesionerBottom) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new kuesionerFragmenBott()).commit();
            title.setText("Kuesioner");
        } else if (id == R.id.mapBottom) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new mapFragmentBott()).commit();
            title.setText("Peta");
        } else if (id == R.id.dataBottom) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new dataFragmentBott()).commit();
            title.setText("Data Korupsi Provinsi");
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    private void logOut() {
        // Clear the token from SharedPreferences
        SharedPreferences prefs = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove("token");
        editor.putBoolean("isLoggedIn", false); // Optional: Update the login status
        editor.apply();

        // Navigate to the login activity
        Intent loginActivity = new Intent(MainMenu.this, LoginActivity.class);
        loginActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginActivity);
        finish();
    }

    public void keluar() {
        new AlertDialog.Builder(this)
                .setMessage("Anda ingin keluar?")
                .setPositiveButton("OK", (dialog, which) -> logOut())
                .create()
                .show();
    }

    private void loadSavedData() {
        SharedPreferences prefs = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        String name = prefs.getString("name", "");
        String email = prefs.getString("email", "");

        NavigationView navigationView = findViewById(R.id.nav_view);
        TextView nameTextView = navigationView.getHeaderView(0).findViewById(R.id.name);
        TextView emailTextView = navigationView.getHeaderView(0).findViewById(R.id.email);

        nameTextView.setText(name);
        emailTextView.setText(email);
    }
}
