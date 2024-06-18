package com.example.corruptionperceptionindex.src.register;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.corruptionperceptionindex.R;
import com.example.corruptionperceptionindex.src.connection.FetchQuestionsTask;

import java.util.ArrayList;
import java.util.List;

public class fourthActivity extends Fragment implements FetchQuestionsTask.FetchQuestionsCallback {

    TextView question1, question2, question3;
    Spinner question1Spinner, question2Spinner, question3Spinner;
    Button nextButton;
    private ViewPager2 viewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fourth, container, false);
        question1 = view.findViewById(R.id.question1);
        question2 = view.findViewById(R.id.question2);
        question3 = view.findViewById(R.id.question3);

        question1Spinner = view.findViewById(R.id.question1Spiner);
        question2Spinner = view.findViewById(R.id.question2Spinner);
        question3Spinner = view.findViewById(R.id.question3Spiner);

        nextButton = view.findViewById(R.id.btn_login);
        viewPager = getActivity().findViewById(R.id.viewPager);

        List<String> spinnerOptions = new ArrayList<>();
        spinnerOptions.add("Tidak Efektif");
        spinnerOptions.add("Efektif");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, spinnerOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        question1Spinner.setAdapter(adapter);
        question2Spinner.setAdapter(adapter);
        question3Spinner.setAdapter(adapter);

        new FetchQuestionsTask(requireContext(), this).execute();

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveResponses();
                navigateToNextStep();
            }
        });

        return view;
    }

    private void navigateToNextStep() {
        // Navigate to the next page in the ViewPager2
        if (viewPager != null) {
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
        }
    }

    private void saveResponses() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Responses", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("question1_response", question1Spinner.getSelectedItem().toString());
        editor.putString("question2_response", question2Spinner.getSelectedItem().toString());
        editor.putString("question3_response", question3Spinner.getSelectedItem().toString());
        editor.apply();
    }

    @Override
    public void onQuestionsFetched(List<String> questions) {
        if (questions.size() >= 3) {
            question1.setText(questions.get(0));
            question2.setText(questions.get(1));
            question3.setText(questions.get(2));
        }
    }
}
