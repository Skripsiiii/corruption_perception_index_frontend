package com.example.corruptionperceptionindex.src.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.corruptionperceptionindex.R;

import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class dataProvinsiAdapter extends RecyclerView.Adapter<dataProvinsiAdapter.ViewHolder> {

    private List<String> provinsiList;
    private OnItemClickListener listener;

    // Antarmuka untuk menangani klik item
    public interface OnItemClickListener {
        void onItemClick(String provinsi);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public dataProvinsiAdapter(List<String> provinsiList) {
        this.provinsiList = provinsiList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_data_provinsi_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String provinsi = provinsiList.get(position);
        holder.namaProvinsi.setText(provinsi);
    }

    @Override
    public int getItemCount() {
        return provinsiList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView namaProvinsi;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            namaProvinsi = itemView.findViewById(R.id.namaProvinsi);

            // Menambahkan onClickListener ke itemView
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        // buat dapetin nama provinsinya
                        String provinsi = provinsiList.get(position);
                        listener.onItemClick(provinsi);
                    }
                }
            });
        }
    }
}