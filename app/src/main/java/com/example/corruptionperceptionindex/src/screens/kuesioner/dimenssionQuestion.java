package com.example.corruptionperceptionindex.src.screens.kuesioner;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.VideoView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.corruptionperceptionindex.R;
import com.example.corruptionperceptionindex.src.fragmentsKuesioner.dimenssionFirst;

public class dimenssionQuestion extends AppCompatActivity {

    Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dimenssion_question);

        nextButton = findViewById(R.id.btn_next);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAnimatedDialog();
            }
        });

        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            dimenssionFirst fragment = new dimenssionFirst();
            transaction.replace(R.id.fragment_container, fragment);
            transaction.commit();
        }
    }

    private void showAnimatedDialog() {
        // Inflate the dialog layout
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_with_video_animation, null);

        ImageView animationView = dialogView.findViewById(R.id.animation_view);
        Glide.with(this)
                .asGif()
                .load(R.drawable.oldmananimation)
                .into(animationView);


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView)
                .setPositiveButton("Lanjutkan", null)
                .create()
                .show();
    }


}
