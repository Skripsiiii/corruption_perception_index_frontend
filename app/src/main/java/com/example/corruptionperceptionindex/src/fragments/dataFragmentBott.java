package com.example.corruptionperceptionindex.src.fragments;

import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.corruptionperceptionindex.R;
import com.example.corruptionperceptionindex.src.connection.FetchProvinceIndexTask;
import com.example.corruptionperceptionindex.src.fragmentsData.dataProvinsiFirstFragment;
import com.example.corruptionperceptionindex.src.fragmentsData.dataProvinsiSecondFragment;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class dataFragmentBott extends Fragment {

    private ImageView nextButton, prevButton;
    private TextView countPage, dashboardTv, statusCPI;
    private int currentPage = 1;
    private boolean isSecondFragmentActive = false;
    TextView scoreCPI;
    ProgressBar resulProgress;
    View statusWarnaCPI;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_data_bott, container, false);

        nextButton = view.findViewById(R.id.nextButton);
        prevButton = view.findViewById(R.id.prevButton);
        countPage = view.findViewById(R.id.countPage);
        dashboardTv = view.findViewById(R.id.calculateDashboard);
        scoreCPI = view.findViewById(R.id.calculateDashboard);
        resulProgress = view.findViewById(R.id.circular_progress_bar);
        statusWarnaCPI = view.findViewById(R.id.statusWarnaCPI);
        statusCPI = view.findViewById(R.id.statusCPI);


        countPage.setText(String.valueOf(currentPage));
        loadProvinsiFragment(currentPage);

        nextButton.setOnClickListener(v -> {
            if (!isSecondFragmentActive && currentPage < 4) {
                currentPage++;
                countPage.setText(String.valueOf(currentPage));
                loadProvinsiFragment(currentPage);
            }
        });

        prevButton.setOnClickListener(v -> {
            if (!isSecondFragmentActive && currentPage > 1) {
                currentPage--;
                countPage.setText(String.valueOf(currentPage));
                loadProvinsiFragment(currentPage);
            }
        });

//        try {
//            Map<Integer, String> regionMap = loadkabupatenkotaCode();
//            Map<Integer, String> profMap = loadprovinsiCode();
//            List<Double> questionnaireResults = loadQuestionnaireData();
//            double[] confidenceInterval = calculateConfidenceInterval(questionnaireResults);
//
//            String result = "Confidence Interval for p_1: [" + confidenceInterval[0] + ", " + confidenceInterval[1] + "]";
//            dashboardTv.setText(result);
//        } catch (Exception e) {
//            e.printStackTrace();
//            dashboardTv.setText("Error: " + e.getMessage());
//            Log.e("DataFragmentBott", "Error loading data", e);
//        }
        fetchProvinceIndexData();

        return view;
    }

    @NonNull
    private Map<Integer, String> loadkabupatenkotaCode() throws Exception {
        Map<Integer, String> regionMap = new HashMap<>();
        InputStream inputStream = getResources().openRawResource(R.raw.dummycpi);
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheet("kode_kabupatenkota");

        if (sheet == null) {
            throw new Exception("Sheet 'kode_kabupatenkota' not found");
        }

        for (Row row : sheet) {
            if (row.getRowNum() == 0) { // Skip header
                continue;
            }
            int code = (int) row.getCell(0).getNumericCellValue();
            String name = row.getCell(1).getStringCellValue();
            regionMap.put(code, name);
        }

        workbook.close();
        return regionMap;
    }

    @NonNull
    private Map<Integer, String> loadprovinsiCode() throws Exception {
        Map<Integer, String> profMap = new HashMap<>();
        InputStream inputStream = getResources().openRawResource(R.raw.dummycpi);
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheet("kode_provinsi");

        if (sheet == null) {
            throw new Exception("Sheet 'kode_provinsi' not found");
        }

        for (Row row : sheet) {
            if (row.getRowNum() == 0) { // Skip header
                continue;
            }
            int code = (int) row.getCell(0).getNumericCellValue();
            String name = row.getCell(1).getStringCellValue();
            profMap.put(code, name);
        }

        workbook.close();
        return profMap;
    }

    @NonNull
    private List<Double> loadQuestionnaireData() throws Exception {
        List<Double> results = new ArrayList<>();
        InputStream inputStream = getResources().openRawResource(R.raw.dummycpi);
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheet("dummy_data");

        if (sheet == null) {
            throw new Exception("Sheet 'dummy_data' not found");
        }

        for (Row row : sheet) {
            if (row.getRowNum() == 0) { // Skip header
                continue;
            }
            double p1Score = row.getCell(3).getNumericCellValue(); // Anggap p_1 berada di kolom ke-4 (indeks 3)
            results.add(p1Score);
        }

        workbook.close();
        return results;
    }

    private double[] calculateConfidenceInterval(List<Double> data) {
        DescriptiveStatistics stats = new DescriptiveStatistics();
        for (double num : data) {
            stats.addValue(num);
        }

        double mean = stats.getMean();
        double stdDev = stats.getStandardDeviation();
        double confidenceLevel = 1.96; // 95% confidence interval
        double marginOfError = confidenceLevel * stdDev / Math.sqrt(data.size());

        return new double[]{mean - marginOfError, mean + marginOfError};
    }

    private void loadProvinsiFragment(int pageNumber) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        dataProvinsiFirstFragment firstFragment = new dataProvinsiFirstFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("pageNumber", pageNumber);
        firstFragment.setArguments(bundle);

        firstFragment.setOnProvinsiSelectedListener(provinsi -> loadSecondFragment(provinsi));

        transaction.replace(R.id.fragment_container, firstFragment);
        transaction.commit();
        isSecondFragmentActive = false;
        updateButtonsState();
    }

    private void loadSecondFragment(String provinsi) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        dataProvinsiSecondFragment secondFragment = new dataProvinsiSecondFragment();
        Bundle args = new Bundle();
        args.putString("provinsiName", provinsi);
        secondFragment.setArguments(args);

        transaction.replace(R.id.fragment_container, secondFragment);
        transaction.addToBackStack(null);
        transaction.commit();
        isSecondFragmentActive = true;
        updateButtonsState();
    }

    private void updateButtonsState() {
        if (isSecondFragmentActive) {
            nextButton.setEnabled(false);
            prevButton.setEnabled(false);
        } else {
            nextButton.setEnabled(currentPage < 4);
            prevButton.setEnabled(currentPage > 1);
        }
    }

    private void fetchProvinceIndexData() {
        new FetchProvinceIndexTask(getContext(), averageIndexResult -> {
            scoreCPI.setText(String.format("%.2f", averageIndexResult));
            setProgressBarColorAndStatus(averageIndexResult);
        }).execute();
    }
    private void setProgressBarColorAndStatus(double indexResult) {
        int color;
        String statusText;
        if (indexResult <= 20) {
            color = 0xFFe76f51; // Red
            statusText = "Sangat Korup";
        } else if (indexResult <= 40) {
            color = 0xFFf4a261; // Orange
            statusText = "Korup";
        } else if (indexResult <= 60) {
            color = 0xFFffd966; // Yellow
            statusText = "Netral";
        } else if (indexResult <= 80) {
            color = 0xFF90c8ac; // Light Green
            statusText = "Aman";
        } else {
            color = 0xFF69b3a2; // Green
            statusText = "Sangat Aman";
        }

        resulProgress.getProgressDrawable().setColorFilter(color, PorterDuff.Mode.SRC_IN);
        statusWarnaCPI.setBackgroundColor(color);
        statusCPI.setText(statusText);
    }

}
