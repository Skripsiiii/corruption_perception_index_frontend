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
import com.example.corruptionperceptionindex.src.model.Dimension;
import com.example.corruptionperceptionindex.src.model.Indicator;
import com.example.corruptionperceptionindex.src.model.Question;
import com.example.corruptionperceptionindex.src.screens.kuesioner.dimenssionQuestion;

import java.util.ArrayList;
import java.util.List;

public class dimenssionFirst extends Fragment {

    private RecyclerView recyclerView;
    private QuestionAdapterFirst adapter;
    private Dimension currentDimension;
    private List<Indicator> indicators;
    private int currentIndicatorIndex = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dimenssion_first, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);

        return view;
    }

    public void setDimension(Dimension dimension) {
        currentDimension = dimension;
        indicators = currentDimension.getIndicators();
        currentIndicatorIndex = 0;
        if (!indicators.isEmpty()) {
            loadIndicator(indicators.get(currentIndicatorIndex));
        }
    }

    private void loadIndicator(Indicator indicator) {
        List<Question> questions = indicator.getQuestions();
        if (questions.isEmpty()) {
            if (getActivity() instanceof dimenssionQuestion) {
                ((dimenssionQuestion) getActivity()).showNoMoreIndicatorsMessage();
            }
        } else {
            adapter = new QuestionAdapterFirst(questions);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(adapter);

            if (getActivity() instanceof dimenssionQuestion) {
                ((dimenssionQuestion) getActivity()).setIndicatorTitle(indicator.getName());
                ((dimenssionQuestion) getActivity()).updateProgress(calculateProgress());
            }
        }
    }

    public void nextIndicator() {
        if (currentIndicatorIndex < indicators.size() - 1) {
            currentIndicatorIndex++;
            loadIndicator(indicators.get(currentIndicatorIndex));
        } else if (getActivity() instanceof dimenssionQuestion) {
            ((dimenssionQuestion) getActivity()).showNoMoreIndicatorsMessage();
        }
    }

    public void previousIndicator() {
        if (currentIndicatorIndex > 0) {
            currentIndicatorIndex--;
            loadIndicator(indicators.get(currentIndicatorIndex));
        }
    }

    public boolean hasPreviousIndicators() {
        return currentIndicatorIndex > 0;
    }

    public boolean hasMoreIndicators() {
        return currentIndicatorIndex < indicators.size() - 1;
    }

    private int calculateProgress() {
        if (indicators == null || indicators.isEmpty()) {
            return 0;
        }
        return (int) ((currentIndicatorIndex + 1) / (float) indicators.size() * 100);
    }
}
