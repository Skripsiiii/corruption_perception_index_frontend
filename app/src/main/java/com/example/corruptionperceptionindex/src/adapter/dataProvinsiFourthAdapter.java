package com.example.corruptionperceptionindex.src.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.corruptionperceptionindex.R;
import com.example.corruptionperceptionindex.src.model.IndicatorData;

import java.util.List;

public class dataProvinsiFourthAdapter extends RecyclerView.Adapter<dataProvinsiFourthAdapter.ViewHolder> {

    private List<IndicatorData> indikatorList;

    public dataProvinsiFourthAdapter(List<IndicatorData> indikatorList) {
        this.indikatorList = indikatorList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_data_provinsi_fourth_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        IndicatorData indikator = indikatorList.get(position);
        holder.indikatorTextView.setText(indikator.getName());
        holder.statusTextView.setText(String.valueOf(indikator.getIndexResult()));
    }

    @Override
    public int getItemCount() {
        return indikatorList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView indikatorTextView, statusTextView;

        ViewHolder(View itemView) {
            super(itemView);
            indikatorTextView = itemView.findViewById(R.id.dimensiTxt);
            statusTextView = itemView.findViewById(R.id.statusText);
        }
    }
}
