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

public class fifthRegister extends Fragment implements FetchQuestionsTask.FetchQuestionsCallback {

    TextView question4, question5, question6;
    Spinner question4Spinner, question5Spinner, question6Spinner;
    Button nextButton;
    private ViewPager2 viewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fifth_register, container, false);
        question4 = view.findViewById(R.id.question4);
        question5 = view.findViewById(R.id.question5);
        question6 = view.findViewById(R.id.question6);

        question4Spinner = view.findViewById(R.id.question4Spinner);
        question5Spinner = view.findViewById(R.id.question5Spinner);
        question6Spinner = view.findViewById(R.id.question6Spinner);

        nextButton = view.findViewById(R.id.btn_login);

        List<String> spinnerOptions = new ArrayList<>();
        spinnerOptions.add("Tidak Efektif");
        spinnerOptions.add("Efektif");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, spinnerOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        question4Spinner.setAdapter(adapter);
        question5Spinner.setAdapter(adapter);
        question6Spinner.setAdapter(adapter);

        new FetchQuestionsTask(this).execute();

        // Get the ViewPager2 instance from the parent activity
        viewPager = getActivity().findViewById(R.id.viewPager);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveResponses();
                navigateToNextStep();
            }
        });

        return view;
    }

    private void saveResponses() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Responses", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("question4_response", question4Spinner.getSelectedItem().toString());
        editor.putString("question5_response", question5Spinner.getSelectedItem().toString());
        editor.putString("question6_response", question6Spinner.getSelectedItem().toString());
        editor.apply();
    }

    private void navigateToNextStep() {
        // Navigate to the next page in the ViewPager2
        if (viewPager != null) {
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
        }
    }

    @Override
    public void onQuestionsFetched(List<String> questions) {
        if (questions.size() >= 6) {
            question4.setText(questions.get(3));
            question5.setText(questions.get(4));
            question6.setText(questions.get(5));
        }
    }
}
