package com.example.corruptionperceptionindex.src.register;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.corruptionperceptionindex.R;
import com.example.corruptionperceptionindex.src.screens.login.LoginActivity;

public class aktivasiEmail extends AppCompatActivity {

    TextView nameGreetings, textCount, resendButton, backButton;
    Button requestButton;
    private TextView countdown_timer;
    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aktivasi_email);

        nameGreetings = findViewById(R.id.nameTxt);
        textCount = findViewById(R.id.textCount);
        backButton = findViewById(R.id.backButton);
        countdown_timer = findViewById(R.id.countdown_timer);
        requestButton = findViewById(R.id.btnAktivasi);

        requestButton.setVisibility(View.GONE);

        textCount.setVisibility(View.GONE);
        countdown_timer.setVisibility(View.GONE);

        // Receive data from Intent
        String userName = getIntent().getStringExtra("userName");
        nameGreetings.setText("Halo " + userName);

        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGmailApp();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginPage = new Intent(aktivasiEmail.this, LoginActivity.class);
                startActivity(loginPage);
                finish();
            }
        });
    }

    private void openGmailApp() {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "email@example.com", null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Body of the email");

        if (emailIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(emailIntent);
        } else {
            // Handle case where no email app is available
        }
    }

    public Void startTime() {
        timer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                countdown_timer.setText(millisUntilFinished / 1000 + " detik");
            }

            @Override
            public void onFinish() {
                countdown_timer.setVisibility(View.GONE);
                requestButton.setEnabled(true);
                textCount.setVisibility(View.GONE);
            }
        }.start();

        return null;
    }
}
