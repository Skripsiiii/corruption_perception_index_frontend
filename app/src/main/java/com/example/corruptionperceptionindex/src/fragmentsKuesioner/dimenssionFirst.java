package com.example.corruptionperceptionindex.src.fragmentsKuesioner;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.corruptionperceptionindex.R;
import com.example.corruptionperceptionindex.src.adapter.QuestionAdapterFirst;
import com.example.corruptionperceptionindex.src.model.Question;

import java.util.ArrayList;
import java.util.List;

public class dimenssionFirst extends Fragment {

    private RecyclerView recyclerView;
    private QuestionAdapterFirst adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dimenssion_first, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);

        // Dummy data for questions
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("Question 1"));
        questions.add(new Question("Question 2"));
        questions.add(new Question("Question 3"));
        questions.add(new Question("Question 4"));
        questions.add(new Question("Question 5"));
        questions.add(new Question("Question 6"));
        questions.add(new Question("Question 7"));
        questions.add(new Question("Question 9"));
        // Add more questions as needed

        adapter = new QuestionAdapterFirst(questions);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        return view;
    }
}
