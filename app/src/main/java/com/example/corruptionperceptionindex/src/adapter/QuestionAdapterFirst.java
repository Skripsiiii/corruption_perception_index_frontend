package com.example.corruptionperceptionindex.src.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    static class QuestionViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewQuestion;

        public QuestionViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewQuestion = itemView.findViewById(R.id.text_view_question);
        }

        public void bind(Question question) {
            textViewQuestion.setText(question.getQuestionText());
        }
    }
}
