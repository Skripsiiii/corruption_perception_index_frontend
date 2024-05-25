package com.example.corruptionperceptionindex.src.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.corruptionperceptionindex.R;

public class persepsiAdapter extends RecyclerView.Adapter<persepsiAdapter.PersepsiViewHolder> {

    private String[] questions;
    private String[] spinner = {"Tidak Efektif", "Efektif"};

    public persepsiAdapter(String[] questions) {
        this.questions = questions;
    }

    @NonNull
    @Override
    public PersepsiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_persepsi, parent, false);
        return new PersepsiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersepsiViewHolder holder, int position) {
        holder.questionTextView.setText(questions[position]);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(holder.itemView.getContext(),
                android.R.layout.simple_spinner_item, spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.questionSpinner.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return questions.length;
    }

    public static class PersepsiViewHolder extends RecyclerView.ViewHolder {
        TextView questionTextView;
        Spinner questionSpinner;

        public PersepsiViewHolder(@NonNull View itemView) {
            super(itemView);
            questionTextView = itemView.findViewById(R.id.questionPerseptionTxt);
            questionSpinner = itemView.findViewById(R.id.questionSpiner);
        }
    }
}
