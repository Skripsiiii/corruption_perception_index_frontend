package com.example.corruptionperceptionindex.src.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.corruptionperceptionindex.R;

import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.corruptionperceptionindex.R;

import java.util.List;

public class dataProvinsiThirdAdapter extends RecyclerView.Adapter<dataProvinsiThirdAdapter.ViewHolder> {

    private List<String> dimensiList;
    private OnNextButtonClickListener nextButtonClickListener;
    private String selectedDimensi;

    public dataProvinsiThirdAdapter(List<String> dimensiList) {
        this.dimensiList = dimensiList;
    }

    public interface OnNextButtonClickListener {
        void onNextButtonClick();
    }

    public void setOnNextButtonClickListener(OnNextButtonClickListener listener) {
        this.nextButtonClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_data_kabupaten_kota_second_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String dimensi = dimensiList.get(position);
        holder.dimensiTxt.setText(dimensi);

        holder.nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedDimensi = dimensi;
                if (nextButtonClickListener != null) {
                    nextButtonClickListener.onNextButtonClick();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dimensiList.size();
    }

    public String getSelectedDimensi() {
        return selectedDimensi;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView dimensiTxt;
        public ProgressBar progressBar;
        public TextView statusPointText;
        public ImageView nextButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dimensiTxt = itemView.findViewById(R.id.dimensiTxt);
            progressBar = itemView.findViewById(R.id.progressBar);
            statusPointText = itemView.findViewById(R.id.statusPointText);
            nextButton = itemView.findViewById(R.id.nextButton);
        }
    }
}
