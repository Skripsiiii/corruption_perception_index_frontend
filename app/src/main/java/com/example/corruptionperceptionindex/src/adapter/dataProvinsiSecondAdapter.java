package com.example.corruptionperceptionindex.src.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.corruptionperceptionindex.R;

import java.util.List;

public class dataProvinsiSecondAdapter extends RecyclerView.Adapter<dataProvinsiSecondAdapter.ViewHolder> {

    private List<String> kabupatenKotaList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(String kabupatenKota);
    }

    public dataProvinsiSecondAdapter(List<String> kabupatenKotaList, OnItemClickListener listener) {
        this.kabupatenKotaList = kabupatenKotaList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_data_provinsi_second_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String kabupatenKota = kabupatenKotaList.get(position);
        holder.namaKabupatenKotaTextView.setText(kabupatenKota);
        holder.itemView.setOnClickListener(v -> listener.onItemClick(kabupatenKota));
    }

    @Override
    public int getItemCount() {
        return kabupatenKotaList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView namaKabupatenKotaTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            namaKabupatenKotaTextView = itemView.findViewById(R.id.namaProvinsi);
        }
    }
}
