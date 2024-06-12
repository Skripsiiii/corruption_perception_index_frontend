package com.example.corruptionperceptionindex.src.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.corruptionperceptionindex.R;
import com.example.corruptionperceptionindex.src.model.Question;

import java.util.List;

public class QuestionAdapterFirst extends RecyclerView.Adapter<QuestionAdapterFirst.QuestionViewHolder> {

    private List<Question> questions;

    public QuestionAdapterFirst(List<Question> questions) {
        this.questions = questions;
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_question_adapter_first, parent, false);
        return new QuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        Question question = questions.get(position);
        holder.bind(question);
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    class QuestionViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewQuestion;
        private RadioGroup radioGroup;

        public QuestionViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewQuestion = itemView.findViewById(R.id.text_view_question);
            radioGroup = itemView.findViewById(R.id.radioGroup);
        }

        public void bind(Question question) {
            textViewQuestion.setText(question.getQuestionText());

            // Set the previously selected answer if exists
            if (question.getSelectedAnswer() != -1) {
                ((RadioButton) radioGroup.getChildAt(question.getSelectedAnswer())).setChecked(true);
            }

            // Handle radio button selection
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    int selectedAnswer = group.indexOfChild(itemView.findViewById(checkedId));
                    question.setSelectedAnswer(selectedAnswer);
                }
            });
        }
    }
}
