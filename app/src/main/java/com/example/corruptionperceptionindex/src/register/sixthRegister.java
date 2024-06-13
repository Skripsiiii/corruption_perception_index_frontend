package com.example.corruptionperceptionindex.src.register;

import android.content.Context;
import android.content.Intent;
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

import com.example.corruptionperceptionindex.R;
import com.example.corruptionperceptionindex.src.connection.FetchQuestionsTask;
import com.example.corruptionperceptionindex.src.screens.mainPage.MainMenu;

import java.util.ArrayList;
import java.util.List;

public class sixthRegister extends Fragment implements FetchQuestionsTask.FetchQuestionsCallback {

    TextView question7, question8;
    Spinner question7Spinner, question8Spinner;
    Button nextButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_sixth_register, container, false);

        question7 = view.findViewById(R.id.question7);
        question8 = view.findViewById(R.id.question8);
        question7Spinner = view.findViewById(R.id.question7Spinner);
        question8Spinner = view.findViewById(R.id.question8Spinner);
        nextButton = view.findViewById(R.id.nextButton);

        List<String> spinnerOptions = new ArrayList<>();
        spinnerOptions.add("Tidak Efektif");
        spinnerOptions.add("Efektif");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, spinnerOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        question7Spinner.setAdapter(adapter);
        question8Spinner.setAdapter(adapter);

        new FetchQuestionsTask(this).execute();

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveResponses();
                Intent mainmenuPage = new Intent(getActivity(), MainMenu.class);
                startActivity(mainmenuPage);
                getActivity().finish();
            }
        });

        return view;
    }

    private void saveResponses() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Responses", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("question7_response", question7Spinner.getSelectedItem().toString());
        editor.putString("question8_response", question8Spinner.getSelectedItem().toString());
        editor.apply();
    }

    @Override
    public void onQuestionsFetched(List<String> questions) {
        if (questions.size() >= 8) {
            question7.setText(questions.get(6));
            question8.setText(questions.get(7));
        }
    }
}
