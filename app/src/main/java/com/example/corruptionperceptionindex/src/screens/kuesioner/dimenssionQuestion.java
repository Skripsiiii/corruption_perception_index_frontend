package com.example.corruptionperceptionindex.src.screens.kuesioner;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.corruptionperceptionindex.R;
import com.example.corruptionperceptionindex.src.fragmentsKuesioner.dimenssionFirst;

public class dimenssionQuestion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dimenssion_question);

        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            dimenssionFirst fragment = new dimenssionFirst();
            transaction.replace(R.id.fragment_container, fragment);
            transaction.commit();
        }
    }
}
