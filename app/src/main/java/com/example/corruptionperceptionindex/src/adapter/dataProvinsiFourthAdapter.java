package com.example.corruptionperceptionindex.src.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.corruptionperceptionindex.R;

import java.util.List;

public class dataProvinsiFourthAdapter extends RecyclerView.Adapter<dataProvinsiFourthAdapter.ViewHolder> {

    private List<String> indikatorList;

    public dataProvinsiFourthAdapter(List<String> indikatorList) {
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
        String indikator = indikatorList.get(position);
        holder.indikatorTextView.setText(indikator);

        holder.indikatorTextView.setText(indikator);
//        holder.statusTextView.setText(data.getStatus());
//        holder.cpiTextView.setText(data.getCpi());
    }

    @Override
    public int getItemCount() {
        return indikatorList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView indikatorTextView, statusTextView, cpiTextView;

        ViewHolder(View itemView) {
            super(itemView);
            indikatorTextView = itemView.findViewById(R.id.dimensiTxt);
            statusTextView = itemView.findViewById(R.id.statusText);
            cpiTextView = itemView.findViewById(R.id.statusCPI);
        }
    }
}
