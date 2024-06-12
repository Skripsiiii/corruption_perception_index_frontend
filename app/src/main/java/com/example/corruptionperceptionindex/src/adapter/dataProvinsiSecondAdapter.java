package com.example.corruptionperceptionindex.src.adapter;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.corruptionperceptionindex.R;
import com.example.corruptionperceptionindex.src.fragmentsData.dataProvinsiThirdFragment;
import com.example.corruptionperceptionindex.src.model.CityData;

import java.util.List;

public class dataProvinsiSecondAdapter extends RecyclerView.Adapter<dataProvinsiSecondAdapter.ViewHolder> {

    private List<CityData> cityDataList;
    private OnItemClickListener listener;
    private String provinsiName;
    private int indexResult;

    public interface OnItemClickListener {
        void onItemClick(String kabupatenKota);
    }

    public dataProvinsiSecondAdapter(List<CityData> cityDataList, OnItemClickListener listener, String provinsiName, int indexResult) {
        this.cityDataList = cityDataList;
        this.listener = listener;
        this.provinsiName = provinsiName;
        this.indexResult = indexResult;
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
        CityData cityData = cityDataList.get(position);
        holder.namaKabupatenKotaTextView.setText(cityData.getName());
        holder.statusPointText.setText(cityData.getIndexResult() + "/100");
        holder.progressBar.setProgress((int) (cityData.getIndexResult()));

        int color;
        String statusText;
        if (cityData.getIndexResult() <= 20) {
            color = 0xFFe76f51; // Red
            statusText = "Sangat Korup";
        } else if (cityData.getIndexResult() <= 40) {
            color = 0xFFf4a261; // Orange
            statusText = "Korup";
        } else if (cityData.getIndexResult() <= 60) {
            color = 0xFFffd966; // Yellow
            statusText = "Netral";
        } else if (cityData.getIndexResult() <= 80) {
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

//        holder.nextButton.setOnClickListener(v -> {
//            Bundle bundle = new Bundle();
//            bundle.putString("provinsiName", provinsiName);
//            bundle.putString("kabupatenKota", cityData.getName());
//            bundle.putInt("cityId", cityData.getId());
//            bundle.putInt("indexResult", indexResult);
//
//            dataProvinsiThirdFragment thirdFragment = new dataProvinsiThirdFragment();
//            thirdFragment.setArguments(bundle);
//
//            ((FragmentActivity) holder.itemView.getContext())
//                    .getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.fragment_container, thirdFragment)
//                    .addToBackStack(null)
//                    .commit();
//        });

        holder.nextButton.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("provinsiName", provinsiName);
            bundle.putString("kabupatenKota", cityData.getName());
            bundle.putInt("cityId", cityData.getId());
            bundle.putInt("indexResult", indexResult);
            bundle.putInt("indexResultKabupatenKota", (int) cityData.getIndexResult()); // Add this line to pass indexResult

            dataProvinsiThirdFragment thirdFragment = new dataProvinsiThirdFragment();
            thirdFragment.setArguments(bundle);

            ((FragmentActivity) holder.itemView.getContext())
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, thirdFragment)
                    .addToBackStack(null)
                    .commit();
        });


    }

    @Override
    public int getItemCount() {
        return cityDataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView namaKabupatenKotaTextView, statusPointText, statusWarnaText;
        ProgressBar progressBar;
        View statusBar;
        ImageView nextButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            namaKabupatenKotaTextView = itemView.findViewById(R.id.namaProvinsi);
            statusPointText = itemView.findViewById(R.id.statusPointText);
            statusWarnaText = itemView.findViewById(R.id.statusWarnaText);
            progressBar = itemView.findViewById(R.id.progressBar);
            statusBar = itemView.findViewById(R.id.statusBar);
            nextButton = itemView.findViewById(R.id.nextButton);
        }
    }
}
