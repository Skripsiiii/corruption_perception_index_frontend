package com.example.corruptionperceptionindex.src.screens.kuesioner;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.corruptionperceptionindex.R;
import com.example.corruptionperceptionindex.src.connection.FetchDimensionsTask;
import com.example.corruptionperceptionindex.src.fragmentsKuesioner.dimenssionFirst;
import com.example.corruptionperceptionindex.src.model.Dimension;
import com.example.corruptionperceptionindex.src.model.Question;
import com.example.corruptionperceptionindex.src.connection.Koneksi;

import java.util.HashMap;
import java.util.List;

public class dimenssionQuestion extends AppCompatActivity {

    Button nextButton, prevButton;
    TextView titleDimenssion;
    TextView titleIndicator, namatahundaerahTV;
    ProgressBar progressBar;
    TextView progressPresentation;
    dimenssionFirst fragment;
    private List<Dimension> dimensions;
    private int currentDimensionIndex = 0;
    com.google.android.material.appbar.MaterialToolbar close;

    private static final String PREFS_NAME = "QuestionnairePrefs";
    private SharedPreferences sharedPreferences;

    private static final String COUNTER_PREF = "counter_pref";
    private int clickCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dimenssion_question);

        nextButton = findViewById(R.id.btn_next);
        titleDimenssion = findViewById(R.id.judulDimensiTxt);
        titleIndicator = findViewById(R.id.judulIndikatorTxt);
        prevButton = findViewById(R.id.btn_prev);
        progressBar = findViewById(R.id.progress_bar);
        progressPresentation = findViewById(R.id.progressPresentation);
        close = findViewById(R.id.close);
        namatahundaerahTV = findViewById(R.id.tahunDaerahTxt);

        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        clickCounter = sharedPreferences.getInt(COUNTER_PREF, 0);

        titleDimenssion.setVisibility(View.GONE);

        // Membaca data dari SharedPreferences
        SharedPreferences prefs = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        String tahun = prefs.getString("tahun", "");
        String provinsi = prefs.getString("selectprovinsi", "");
        String kabupaten = prefs.getString("selectkabupaten", "");

        // Log data yang dibaca
        Log.d("dimenssionQuestion", "Loaded data - Tahun: " + tahun + ", Provinsi: " + provinsi + ", Kabupaten: " + kabupaten);

        // Mengisi namatahundaerahTV
        namatahundaerahTV.setText(tahun + " - " + provinsi + "\n" + kabupaten);

        prevButton.setVisibility(View.GONE); // Initially hide the prevButton

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fragment != null) {
                    fragment.nextIndicator(); // This will handle the validation and alert

                    prevButton.setVisibility(View.VISIBLE); // Show prevButton after navigating to the next indicator
                }
            }
        });

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPreviousIndicator();
            }
        });

        close.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                saveAnswers();
                finish();
            }
        });

        currentDimensionIndex = getIntent().getIntExtra("currentDimensionIndex", 0);
        clickCounter = sharedPreferences.getInt(COUNTER_PREF, 0);

        Koneksi koneksi = new Koneksi();
        String apiUrl = koneksi.connquestions();

        new FetchDimensionsTask(apiUrl, new FetchDimensionsTask.OnDimensionsFetchedListener() {
            @Override
            public void onDimensionsFetched(List<Dimension> dimensionsList) {
                dimensions = dimensionsList;
                if (!dimensions.isEmpty()) {
                    loadDimension(dimensions.get(currentDimensionIndex));
                }
            }
        }).execute();

        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            fragment = new dimenssionFirst();
            transaction.replace(R.id.fragment_container, fragment);
            transaction.commit();
        }
    }

    private void loadDimension(Dimension dimension) {
        if (fragment != null) {
            fragment.setDimension(dimension);
        }
        setDimensionTitle(dimension.getName());
        restoreAnswers(dimension); // Restore answers when a dimension is loaded
    }

    public void setDimensionTitle(String title) {
        titleDimenssion.setText(title);
    }

    public void setIndicatorTitle(String title) {
        titleIndicator.setText(title);
    }

    public void updateProgress(int progress) {
        progressBar.setProgress(progress);
        progressPresentation.setText(progress + "%");
    }

    public void showAnimatedDialog() {
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
                .setPositiveButton("Lanjutkan", (dialog, which) -> {
//                    if (fragment != null) {
//                        fragment.nextIndicator(); // Proceed to the next indicator
//                        prevButton.setVisibility(View.VISIBLE); // Show prevButton after navigating to the next indicator
//                    }
                })
                .create()
                .show();
    }

    private void showPreviousIndicator() {
        if (fragment != null) {
            fragment.previousIndicator(); // Navigate to the previous indicator
            if (!fragment.hasPreviousIndicators()) {
                prevButton.setVisibility(View.GONE); // Hide prevButton if there are no previous indicators
            }
        }
    }

    public void showNoQuestionsDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Tidak ada pertanyaan")
                .setMessage("Tidak ada pertanyaan untuk indikator ini. Lanjutkan ke indikator berikutnya.")
                .setPositiveButton("OK", (dialog, which) -> {
                    if (fragment != null) {
                        fragment.nextIndicator();
                    }
                })
                .create()
                .show();
    }

    public void showNoMoreIndicatorsMessage() {
        new AlertDialog.Builder(this)
                .setTitle("Selesai")
                .setMessage("Mau melanjutkan isi kuesioner?")
                .setPositiveButton("OK", (dialog, which) -> {
                    saveAnswers(); // Save answers before moving to the next dimension
                    currentDimensionIndex++;
                    clickCounter++; // Increment the counter
                    sharedPreferences.edit().putInt(COUNTER_PREF, clickCounter).apply(); // Save the counter to SharedPreferences

                    updateProgress(); // Update the progress in SharedPreferences

                    if (currentDimensionIndex < dimensions.size()) {
                        loadDimension(dimensions.get(currentDimensionIndex));
                        prevButton.setVisibility(View.GONE); // Hide prevButton when a new dimension is loaded
                    } else {
                        finish(); // All dimensions are completed
                    }
                })
                .create()
                .show();
    }

    private void updateProgress() {
        int progress = (int) ((clickCounter / 17.0) * 100);
        sharedPreferences.edit()
                .putInt("progress", progress)
                .putInt("clickCounter", clickCounter)
                .apply();
    }

    private void saveAnswers() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (Dimension dimension : dimensions) {
            for (Question question : dimension.getQuestions()) {
                int selectedAnswer = question.getSelectedAnswer();
                editor.putInt("question_" + question.getId(), selectedAnswer);
            }
        }
        editor.apply();
    }

    private void restoreAnswers(Dimension dimension) {
        for (Question question : dimension.getQuestions()) {
            int savedAnswer = sharedPreferences.getInt("question_" + question.getId(), -1);
            if (savedAnswer != -1) {
                question.setSelectedAnswer(savedAnswer);
            }
        }
    }

    public void showIncompleteQuestionsAlert() {
        new AlertDialog.Builder(this)
                .setTitle("Peringatan")
                .setMessage("Anda belum menyelesaikannya")
                .setPositiveButton("OK", null)
                .create()
                .show();
    }
}
