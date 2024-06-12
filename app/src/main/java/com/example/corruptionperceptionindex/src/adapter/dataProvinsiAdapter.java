package com.example.corruptionperceptionindex.src.adapter;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.corruptionperceptionindex.R;
import com.example.corruptionperceptionindex.src.fragmentsData.dataProvinsiSecondFragment;
import com.example.corruptionperceptionindex.src.model.ProvinceData;

import java.util.List;

public class dataProvinsiAdapter extends RecyclerView.Adapter<dataProvinsiAdapter.ViewHolder> {

    private List<ProvinceData> provinceDataList;
    private OnItemClickListener listener;

    // Interface for handling item clicks
    public interface OnItemClickListener {
        void onItemClick(ProvinceData provinceData);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public dataProvinsiAdapter(List<ProvinceData> provinceDataList) {
        this.provinceDataList = provinceDataList;
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
        ProvinceData provinceData = provinceDataList.get(position);
        holder.namaProvinsi.setText(provinceData.getName());
        holder.statusPointText.setText(provinceData.getIndexResult() + "/100");
        holder.progressBar.setProgress((int) (provinceData.getIndexResult()));

        int color;
        String statusText;
        if (provinceData.getIndexResult() <= 20) {
            color = 0xFFe76f51; // Red
            statusText = "Sangat Korup";
        } else if (provinceData.getIndexResult() <= 40) {
            color = 0xFFf4a261; // Orange
            statusText = "Korup";
        } else if (provinceData.getIndexResult() <= 60) {
            color = 0xFFffd966; // Yellow
            statusText = "Netral";
        } else if (provinceData.getIndexResult() <= 80) {
            color = 0xFF90c8ac; // Light Green
            statusText = "Aman";
        } else {
            color = 0xFF69b3a2; // Green
            statusText = "Sangat Aman";
        }

        holder.statusBar.setBackgroundColor(color);
        holder.statusWarnaText.setBackgroundColor(color);
        holder.statusWarnaText.setText(statusText);

        // Customize ProgressBar color
        LayerDrawable progressDrawable = (LayerDrawable) holder.progressBar.getProgressDrawable();
        Drawable progressLayer = progressDrawable.findDrawableByLayerId(android.R.id.progress);
        progressLayer.setColorFilter(color, PorterDuff.Mode.SRC_IN);

        // Set up next button click listener
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(provinceData);

                // Pass data to the second fragment
                dataProvinsiSecondFragment secondFragment = new dataProvinsiSecondFragment();
                Bundle bundle = new Bundle();
                bundle.putString("provinsiName", provinceData.getName());
                bundle.putInt("provinceId", provinceData.getId());
                bundle.putInt("indexResult", (int) provinceData.getIndexResult());
                secondFragment.setArguments(bundle);

                ((FragmentActivity) holder.itemView.getContext())
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, secondFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return provinceDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView namaProvinsi, statusPointText, statusWarnaText;
        ProgressBar progressBar;
        View statusBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            namaProvinsi = itemView.findViewById(R.id.namaProvinsi);
            statusPointText = itemView.findViewById(R.id.statusPointText);
            progressBar = itemView.findViewById(R.id.progressBar);
            statusBar = itemView.findViewById(R.id.statusBar);
            statusWarnaText = itemView.findViewById(R.id.statusWarnaText);
        }
    }
}
